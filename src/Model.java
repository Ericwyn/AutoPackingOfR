import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Create the model of R package files and folders
 *
 * Created by Ericwyn on 17-10-12.
 */
public class Model {

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    /**
     * create the folders of R package file
     *
     * the tree of the created folder is like this
     *      once
     *        ├── data
     *        ├── DESCRIPTION
     *        ├── man
     *        ├── NAMESPACE
     *        ├── R
     *        │   └── XXXX.R
     *        └── src
     *
     * @param folderPath the path of the folder
     * @param rFilePath the list of the R language file path
     *
     * @return return the root path of if the R package folder was created successfully,else return <code>null</code>
     */
    public static String createPackageFolder( String folderPath ,ArrayList<String> rFilePath){
        File floderRoot=new File(folderPath);
        String[] fileNameTemp=floderRoot.list();
        if(fileNameTemp!=null && fileNameTemp.length!=0){
            System.out.println("ERROR : the root floder of this package is not empty !");
            return null;
        }

        for (String str:rFilePath){
            if(!new File(str).isFile()){
                System.out.println("the R language file Path \""+str+"\" is wrong");
                return null;
            }
        }

        File floderData=new File(floderRoot,"data");
        File floderMan=new File(floderRoot,"man");
        File floderR=new File(floderRoot,"R");
        File floderSrc=new File(floderRoot,"src");

        boolean success1=floderData.mkdirs();
        boolean success2=floderMan.mkdirs();
        boolean success3=floderR.mkdirs();
        boolean success4=floderSrc.mkdirs();

        if(success1 && success2 && success3 && success4){
            //copy R file to R floder
            for(String fileTemp:rFilePath){
                copyFile(new File(fileTemp),new File(floderR,new File(fileTemp).getName()));
            }
            //create DESCRIPTION file
            if(!createDESCRIPTION(folderPath)){
                return null;
            }else {
                if(!createNAMESPACE(folderPath)){
                    return null;
                }else {
                    System.out.println("================================");
                    System.out.println();
                    System.out.println("create R package floder success");
                    System.out.println();
                    System.out.println("the path : "+folderPath);
                    System.out.println();
                    System.out.println(" and now , open the R CMD check");
                    System.out.println();
                    System.out.println("================================");
                    System.out.println();
                    return folderPath;
                }
            }
        }else {
            System.out.println("Failed to create template folder in path :" + folderPath);
        }
        return null;
    }

    private static void copyFile(File from,File to){
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(from));
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(to));
            String line =null;
            while ((line=bufferedReader.readLine())!=null){
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedReader.close();
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println();
        }
    }


    /**
     *
     * @param rootPath the root path of the R package
     *
     * @return success created or not
     */
    private static boolean createDESCRIPTION(String rootPath){
        File file=new File(new File(rootPath),"DESCRIPTION");
        if(!file.isFile()){
            try {
                boolean flag = file.createNewFile();
                if(!flag){
                    System.out.println("create DESCRIPTION file failed");
                    return false;
                }
                BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));
                Scanner scanner=new Scanner(System.in);
                System.out.println("please input the Package value of in DESCRIPTION , use \"Test Package\" default :");
                String Package =scanner.nextLine();
                if(Package.isEmpty() || Package.replaceAll(" ","").equals("")){
                    Package ="test";
                }
                System.out.println("please input the Version value of in DESCRIPTION ( like \"0.1\" ): ");
                String Version = scanner.nextLine();
                if(Version.isEmpty() || Version.replaceAll(" ","").equals("")){
                    Version ="0.1";
                }

                System.out.println("please input the Date value of in DESCRIPTION ( like \"1997-05-04\" ) ,use \""+sdf.format(new Date())+"\"default: ");
                String Data = scanner.nextLine();
                if(Data.isEmpty() || Data.replaceAll(" ","").equals("")){
                    Data =sdf.format(new Date());
                }
                System.out.println("please input the Title value of in DESCRIPTION , use \"test_title\" default: ");
                String Title = scanner.nextLine();
                if(Title.isEmpty() || Title.replaceAll(" ","").equals("")){
                    Title ="test_title";
                }
                System.out.println("please input the Author value of in DESCRIPTION ( like \" Ericwyn Chen (Ericwyn.chen@gmail.com)\" ,use \"admin (admin@admin.com) default\"): ");
                String Author = scanner.nextLine();
                if(Author.isEmpty() || Author.replaceAll(" ","").equals("")){
                    Author ="admin <admin@admin.com>";
                }
                System.out.println("please input the Maintainer value of in DESCRIPTION ( like \" Ericwyn Chen (Ericwyn.chen@gmail.com)\" ,use \"admin (admin@admin.com) default\"): ");
                String Maintainer = scanner.nextLine();
                if(Maintainer.isEmpty() || Maintainer.replaceAll(" ","").equals("")){
                    Maintainer ="admin <admin@admin.com>";
                }
                System.out.println("please input the Depends value of in DESCRIPTION ( like \"R (>= 1.9.0)\" ),use \"R (>= 1.9.0)\" default : ");
                String Depends = scanner.nextLine();
                if(Depends.isEmpty() || Depends.replaceAll(" ","").equals("")){
                    Depends ="R (>= 1.9.0)";
                }
                System.out.println("please input the Description value of in DESCRIPTION : ");
                String Description = scanner.nextLine();
                if(Description.isEmpty() || Description.replaceAll(" ","").equals("")){
                    Description ="the package auto packing by APOR (https://www.github.com/Ericwyn/AutoPackingR) ";
                }
                System.out.println("please input the License value of in DESCRIPTION ( like \"GPL version 2 or later\" ) , use \"GPL version 2 or later\" default : ");
                String License = scanner.nextLine();
                if(License.isEmpty() || License.replaceAll(" ","").equals("")){
                    License ="GPL version 2 or later";
                }

                bufferedWriter.write("Package: "+Package);
                bufferedWriter.newLine();
                bufferedWriter.write("Version: "+Version);
                bufferedWriter.newLine();
                bufferedWriter.write("Data: "+Data);
                bufferedWriter.newLine();
                bufferedWriter.write("Title: "+Title);
                bufferedWriter.newLine();
                bufferedWriter.write("Author: "+Author);
                bufferedWriter.newLine();
                bufferedWriter.write("Maintainer: "+Maintainer);
                bufferedWriter.newLine();
                bufferedWriter.write("Depends: "+Depends);
                bufferedWriter.newLine();
                bufferedWriter.write("Description: "+Description);
                bufferedWriter.newLine();
                bufferedWriter.write("License: "+License);

                bufferedWriter.close();
                return true;

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("create DESCRIPTION file failed");
            }
        }
        return false;
    }

    /**
     * create NAMESPACE file
     *
     * @param rootPath the root path of R package
     * @return
     */
    private static boolean createNAMESPACE(String rootPath){
        File file=new File(new File(rootPath),"NAMESPACE");
        try {
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("exportPattern( \".\" )");
            bufferedWriter.flush();
            bufferedWriter.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("create NAMESPACE file failed");
        }
        return false;
    }
}
