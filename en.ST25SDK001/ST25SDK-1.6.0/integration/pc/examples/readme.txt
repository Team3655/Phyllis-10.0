This file instructs you on how to import the Eclipse PC project provided in the ST25 SDK.
To run the executable jar, please refer to the readme file located in st25PcDemoApp\exe\.

Eclipse project:
    1. Import example project
        Open Eclipse
        File > Open Projects from File System...
            Click on Directory... then select the folder called "st25PcDemoApp"
                Click on Finish to import the project

    2. Set the correct path for native reader libraries
        Right-click on your project's name in the Project Explorer View
            Run As...
                Run Configuration
                    Environment Tab in Java Application:
                    For Windows
                    -----------
                        - Add new variable path = ${project_loc:st25PcDemoApp}\..\..\..\..\readers\st\resources\windows\x86\;${env_var:path}
                    For Linux
                    -----------
                        - Add new variable LD_LIBRARY_PATH = <absolute_path_to_ST25SDK>/readers/st/resources/linux/x64

    3. (Optional) Configure path to javadoc file
            The st25sdk javadoc path is hard-coded in the .classpath file.
            Either modify it directly in the file
                or
            Right-click on your project's name in the Project Explorer View
                Properties...
                    Java Build Path menu > Libraries tab
                        Open st25sdk-x.y.z folder and select Javadoc location
                            Click the Edit button and point to the javadoc file (in lib folder of your project)
