The lib folder contains a generic reader implementation and the ReaderInterface. 
The RFGenericReader implements all the methods of the ReaderInterface with a throw of the STExceptionCode.NOT_IMPLEMENTED exception.
It is up to the specific readers to override those methods. Include st25pc-model-readers.jar in your PC program  using an external reader connected via USB.

The st/ folder contains support files for reader discovery kits based on CR95HF, ST25R3911B and ST25R3916:
    - RFReaderInterface implementation
    - JNI and dynamic library dependencies
    
Include the st25pc-model-readers-st.jar library in your PC program if you plan to use the CR95HF, the ST25R3911B-DISCO or the ST25R3916-DISCO reference reader.

The feig/ folder contains support files for FEIG ELECTRONIC's MR101, MR102, LR1002 and CPR30 readers.
Include the st25pc-model-readers-feig.jar library in your PC program if you plan to use one of the FEIG readers.
Also include the OBIDISC4J.jar FEIG Java SDK. The dll (so) files must be configured for run-time.
More information for using FEIG readers can be found on their website: http://www.feig.de/en/

IMPORTANT NOTE: On Linux for all readers, launch install-libs.sh to create symbolic links for the libraries.
    S cd <path_to_your_SDK>/readers/<reader_name>/resources/linux/x64
    $ ./install-libs.sh .