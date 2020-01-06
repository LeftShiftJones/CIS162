import java.text.*;
import java.util.*;
import javax.swing.*;

class MyPhone{
    private boolean wifiOn;
    private int texts;
    private double additionalFees, dataUsed, batteryLife;
    private String customerName, customerPhoneNum;

    public MyPhone(String name, String num){
        wifiOn = false;
        texts = 0;
        dataUsed = 0.00;
        additionalFees = 0.00;
        batteryLife = 1.0;
        customerName = name;
        if(num.length() < 10 || num.length() > 10){
            customerPhoneNum = "9999999999";
        } else{
            customerPhoneNum = num;
        }
        setNum();
    }

    public MyPhone(){
        wifiOn = false;
        texts = 0;
        dataUsed = 0.00;
        additionalFees = 0.00;
        batteryLife = 1.0;
        customerName = "John Doe";
        customerPhoneNum = "9999999999";
        setNum();
    }

    private double dataUse(double data){
        if(wifiOn == true){
            dataUsed += 0;
        } else{
            dataUsed += data;
        }
        return dataUsed;
    }

    private void setNum(){
        customerPhoneNum = ("(" + customerPhoneNum.substring(0,3) + ")-" + 
            customerPhoneNum.substring(3,6) + "-" + 
            customerPhoneNum.substring(6, customerPhoneNum.length()));

        //JOptionPane.showMessageDialog(null, customerPhoneNum);
    }

    private double calcAdditionalDataFee(){
        double extraGigs, incrementGig;
        if((dataUsed / 2000.0) >= 1.0){
            extraGigs = (((int)dataUsed) - 2000) / 1000;
            if(((dataUsed - 2000.0) % 1000) > 0){
                incrementGig = 1;
            } else{
                incrementGig = 0;
            }
            additionalFees = (15 * (extraGigs + incrementGig));
        } else{
            additionalFees = 0.00;
        }
        return additionalFees;
    }

    private void useBattery(double rate, double data){
        double one = 1.0;
        if((batteryLife - rate) < 0.0){
            data *= (one - batteryLife);
            batteryLife = 0.0;
            dataUse(data);
            JOptionPane.showMessageDialog(null, "Battery needs to charge!");            
        } else if(batteryLife == 0.0){
            batteryLife = 0.0;
            JOptionPane.showMessageDialog(null, "Battery needs to charge!");            
        } else{
            batteryLife -= rate;
            dataUse(data);
            JOptionPane.showMessageDialog(null, "Battery life left: " + batteryLife);
        }
    }
    
    public void chargeBattery(int mins){
        
        double batteryCharge = (1/120.0) * mins;
        if(batteryLife == 1){
            JOptionPane.showMessageDialog(null, "ALERT! Battery is full!");
        } else if((batteryLife + batteryCharge) >= 1.0){
            batteryLife = 1.0;
            JOptionPane.showMessageDialog(null, "Battery is at " + batteryLife);
        } else{
            batteryLife += batteryCharge;
            JOptionPane.showMessageDialog(null, "Battery is at " + batteryLife);
        }
    }

    public void setWifi(){
        if(wifiOn == false){
            wifiOn = true;
            JOptionPane.showMessageDialog(null, "Wifi is now On");
        } else{
            wifiOn = false;
            JOptionPane.showMessageDialog(null, "Wifi is now Off");
        }
    }

    public void sendText(){
        texts +=1;
        JOptionPane.showMessageDialog(null, "Have u finished the proj yet??? \n*SENT*");
    }

    public int getNumOfTexts(){
        return texts;
    }

    public double getDataUsage(){
        return dataUsed;
    }
    
    public void getBattery(){
        DecimalFormat fmt = new DecimalFormat("0.0");
        batteryLife *= 100;
        System.out.println("Battery Life: " + fmt.format(batteryLife) + "%");
        batteryLife /= 100;
    }
    
    public void streamAudio(int mins){        
        DecimalFormat fmt = new DecimalFormat ("0.##");
        if(mins <= 0){
            JOptionPane.showMessageDialog(null, "Error: No value entered");
        }
        else if(batteryLife == 0){
            JOptionPane.showMessageDialog(null, "Error: Battery needs charge");
        }
        else{
            final double audioData = 65.0/60.0;
            double audioUse = (1.0/12.0)*(mins/60.0);
            double localAudioData = audioData * mins;
            int hours = mins / 60;
            int minutesLeft = mins % 60;

            System.out.println("You stream audio for " + hours + 
                    " hour(s) and " + minutesLeft + " minutes");
            useBattery(audioUse, localAudioData);
            //dataUse(localAudioData);
            calcAdditionalDataFee();
        }
    }

    public void streamVideo(int mins){
        DecimalFormat fmt = new DecimalFormat ("0.##");
        if(mins <= 0){
            JOptionPane.showMessageDialog(null, "Error: Value cannot be calculated");
        }
        else if(batteryLife == 0){
            JOptionPane.showMessageDialog(null, "Error: Battery needs charge");
        } else{
            final double videoData = 250.0/60.0;
            double videoUse = ((1.0/6.0) * (mins/60.0));
            double localVideoData = videoData * mins;
            int hours = mins / 60;
            int minutesLeft = mins % 60;

            JOptionPane.showMessageDialog(null, ("You stream video for " + 
                    hours + " hours and " + minutesLeft + " minutes"));
            //useBattery(videoUse);
            //dataUse(localVideoData);
            calcAdditionalDataFee();
        }
    }
    
    public void startNewMonth(){
        wifiOn = false;
        dataUsed = 0.00;
        additionalFees = 0.00;
    }
    
    public void printStatement(){
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        DecimalFormat fmt2 = new DecimalFormat("0.##");
        double adminFee = 0.61;
        double twoGigPlan = 50.0;
        double subtotal = (twoGigPlan+additionalFees+adminFee);
        double usageCharge = (subtotal*0.030);
        double finalGig = dataUsed / 1000.0;
        double finalPrice = subtotal + usageCharge;

        //print out monthly statements
        System.out.println("MyPhone Monthly Statement\n");
        System.out.println("Customer:             " + customerName);
        System.out.println("Number:               " + customerPhoneNum);
        System.out.println("Texts:                " + texts);
        System.out.println("Data Usage:           " + fmt2.format(finalGig) + "(GB)");
        System.out.println("Data Usage:           " + fmt2.format(dataUsed) + "(MB)");
        System.out.println("2GB Plan:             " + fmt.format(twoGigPlan));
        System.out.println("Additional data fee:  " + fmt.format(additionalFees));
        System.out.println("Administrative Fee:   " + fmt.format(adminFee));
        System.out.println("Universal Usage (3%): " + fmt.format(usageCharge));
        System.out.println("Total Charges:        " + fmt.format(finalPrice));

    }

}