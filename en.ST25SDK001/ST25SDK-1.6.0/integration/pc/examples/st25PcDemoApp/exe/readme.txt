The example st25PcDemoApp program works with a CR95HF-based reference board, a ST25R3911B-Disco or a ST25R3916-Disco board.
Note: The ST25R3911B-Disco and ST25R3916-Disco boards are only supported on a Windows 32-bit environment.

WINDOWS:
    The Windows version supports CR95HF, ST25R3911B-Disco and ST25R3916 boards in 32-bit configuration only.
    To launch the executable jar from the command line:
    - Move to the <path_to_your_SDK>\integration\pc\examples\st25PcDemoApp\exe directory
    - Add the path to a 32-bit JRE at the beginning of your %PATH% (For example: C:\Program Files (x86)\Java\jdk1.8.0_172\bin\):
        > set PATH="C:\Program Files (x86)\Java\jdk1.8.0_172\bin\";%PATH%
    - Add the ST25SDK's readers\st\resources directory to PATH
        > set PATH=<path_to_your_SDK>\readers\st\resources\windows\x86;%PATH%
        Example:
        > set PATH=..\..\..\..\..\readers\st\resources\windows\x86;%PATH%
    - Launch java -jar command from a terminal in this folder. For example:
        > java -jar st25PcDemoApp.jar

LINUX:
    The Linux version only supports the CR95HF reader in 64-bit configuration. The ST25R3911B-Disco and ST25R3916-Disco boards are not supported on Linux.
    To launch the executable jar from the command line:
    - Add the path to a 64-bit JRE at the beginning of your $PATH
    - Create the symbolic links for the libraries by launching the install-libs.sh script
        S cd <path_to_your_SDK>/readers/st/resources/linux/x64
        $ chmod +x install-libs.sh
        $ ./install-libs.sh .
    - Launch java -jar command while declaring an absolute resource path:
        $ java -Djava.library.path=<absolute_path_to_your_SDK>/readers/st/resources/linux/x64/ -jar st25PcDemoApp.jar

MAC:
    The MacOS version only supports the CR95HF reader in 64-bit configuration. The ST25R3911B-Disco and ST25R3916-Disco boards are not supported on Mac.
    To launch the executable jar from the command line:
    - Add the path to a 64-bit JRE at the beginning of your $PATH
    - Launch java -jar command while declaring an absolute resource path:
        $ java -Djava.library.path=<absolute_path_to_your_SDK>/readers/st/resources/mac/x64/ -jar st25PcDemoApp.jar