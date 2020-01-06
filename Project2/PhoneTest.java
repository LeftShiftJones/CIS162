import java.text.*;

public class PhoneTest{
    public static void main(String args[]){
        String customer, phoneNum;
        customer = "John Doe";
        phoneNum = "1234567890";
        MyPhone phone = new MyPhone(customer, phoneNum);
        phone.getBattery();
        phone.streamAudio(6*60);
        phone.getBattery();
        phone.streamAudio(6*60);
        phone.printStatement();
    }
}