package MineGithubRepo;


import java.awt.geom.RectangularShape;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


import java.io.FileNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

import refdiff.core.RefDiff;
import refdiff.core.api.GitService;
import refdiff.core.rm2.model.refactoring.SDRefactoring;
import refdiff.core.util.GitServiceImpl;




public class MineGithubRepo {

	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		 
        
		try {
			List<String> listOfCommitUrls=new ArrayList<String>();
			String nextURL="https://github.com/saramsh/common-token-subsequences/commits/master";
           // URL url = new URL("https://github.com/saramsh/common-token-subsequences/commits/master");
			System.out.println("Extracting Commits' Sha1...");
			while(nextURL!="")
			{
				
				URL url = new URL(nextURL);           
	            br = new BufferedReader(new InputStreamReader(url.openStream()));            
	            String line;
	            StringBuilder sb = new StringBuilder();
	            while ((line = br.readLine()) != null) {
	
	                sb.append(line);
	                sb.append(System.lineSeparator());
	            }
	            
	            String pageContent=sb.toString();
	            String[] paginationSplit=pageContent.split("<div class=\"paginate-container\" data-pjax data-html-cleaner-suppress-children>");
	            String[] paginationDiv=paginationSplit[1].split("</div>"); 
	            String[] paginationOlder=paginationDiv[0].split("Newer");
	            if(paginationOlder[1].contains("href"))
	            {
	            	nextURL= paginationOlder[1].split("\"")[1];
	            }
	            else
	            {
	            	nextURL="";
	            }
	            //Pattern p=Pattern.compile("<div class=\"table-list-cell\">");
	            String[] divSplit= pageContent.split("<div class=(\"table-list-cell\"|\"commit-meta commit-author-section \")>");
	           for(String divContent : divSplit)
	           {
	        	   if(divContent.contains("class=\"commit-title  \""))
	        	   {
	        		 //  System.out.println("*******"+divContent);
	        		   String[] commitInfo= divContent.split("\"");
	        		   listOfCommitUrls.add(commitInfo[3]);      		   
	        	   }
	           }    
			}
			System.out.println("All Commits' Sha1 are Extracted.");
           PrintWriter pwOut = new PrintWriter(new File("G:\\out.csv"));
	        StringBuilder sbOut = new StringBuilder();
	        sbOut.append("Commit SHA, Number of changes that fit the criteria (Change Method Signature)\n");
            RefDiff refDiff = new RefDiff();
     		GitService gitService = (GitService) new GitServiceImpl();
     		//String project = "https://github.com/saramsh/common-token-subsequences";
     		String project ="https://github.com/saramsh/common-token-subsequences";
     		File folder = new File("G:/tmp5");
     		String projectName = project.split("/")[4];
     		File f = new File(folder, projectName);
     		System.out.println("Creating Local Repository ....");
			Repository repo=gitService.cloneIfNotExists(f.getPath(),  project + ".git");				
			System.out.println("Local Repository is created.");
     		try (PrintStream pw = new PrintStream(new File(folder.getParentFile(), "actual"))) 
      		{    			
      			//Get Commit SHA1
      			for(String commitURL: listOfCommitUrls)
      			{       	  
      				String[] splitURL=commitURL.split("/");
      				// System.out.println("*******"+splitURL[splitURL.length-1]);        	   
      				String sha1 = splitURL[splitURL.length-1]; 	    
      				//try () {
      				try {
          				//System.out.println("aaaaaaaaa");	
          				//	List<SDRefactoring> refactorings = refDiff.detectAtCommit(repo, sha1);
          				List<SDRefactoring> refactorings = refDiff.detectAtCommit(repo, sha1);		
          				//System.out.println("bbbbbbbbbb");
          				System.out.println(String.format("Finished %s %s", project, sha1));
          				int count=0;
          				for (SDRefactoring r : refactorings) {
          					if(r.getRefactoringType().getDisplayName()=="Change Method Signature")
          						count++;
          					System.out.println("Type of Change: "+r.getRefactoringType().getDisplayName());
          						//pw.printf("%s\t%s\t%s\t%s\t%s\n", projectName, sha1, r.getRefactoringType().getDisplayName(), r.getEntityBefore(), r.getEntityAfter());
          						}
          				sbOut.append(sha1+","+ count+"\n");
          						
          				} catch (Exception e) {
          						System.out.println(e);
          						//System.out.println(String.format("Error %s %s: %s", project, sha1, e.getMessage()));
          					}
          		}
      			 pwOut.write(sbOut.toString());
    	         System.out.println("Output CSV file is created.");
    	         pwOut.close();
          	}
     		
		
          			catch (FileNotFoundException e) {
          				throw new RuntimeException(e);
              		}
        	   
             
        	   
           }
                       
         finally {

            if (br != null) {
                br.close();
            }
        }
		// TODO Auto-generated method stub
	}

	
	public static Repository open(File folder) throws IOException {
		RepositoryBuilder builder = new RepositoryBuilder();
		return builder
			.setGitDir(new File(folder, ".git"))
			.readEnvironment()
			.findGitDir()
			.build();
	}
}
