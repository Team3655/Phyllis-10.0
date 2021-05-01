package frc.robot;

public class Music {
    private Music(){
    }

    /**Converts beats per minute to miliseconds per beat
     * 
     * @param bpm
     * @return
     */
    public static long bpmTomspb(int bpm){
        return Math.round((1d/(double)bpm)*60000);
    }
}