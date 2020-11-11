import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServerData {
	public static void main(String argv[]) throws Exception {
		ServerSocket serverSocket = new ServerSocket(1300);
		//ten random bombs
		int total = 100;
		//Improved versions may incorporate a command to determine difficulty or other options
		int b = 10;
		//determines how many bombs remaining can be pressed on grid
		Random bomberMan = new Random(); //Used for randomized bomb placement
		ArrayList<DeepBomb>allPoints = new ArrayList<>();
		//used for data calling 
		for (int m = 0; m<total; m++) {
			int j = bomberMan.nextInt(10); //0-9, 9 == bomb. 1/10 chance of a bomb.
			if (b == 0) { j = 0; }
			if (b >= 100 - m) j = 9;
			allPoints.add(new DeepBomb(j,allPoints));
			if (j == 9) b--;
			}
		
		
		

		System.out.println("Bombs Placed!");
		   while(true) {
		        System.out.println("listening for tile press");
		        Socket connection = serverSocket.accept();
		        System.out.println("Connected");
		        InputStream is = connection.getInputStream();
		        DataOutputStream os = new DataOutputStream(connection.getOutputStream());
		        BufferedReader br = new BufferedReader(new InputStreamReader(is));   
		        
		        String clientMessageX = br.readLine();
		        String clientMessageY = br.readLine();
		        System.out.println("RECEIVED: " + clientMessageX + "x" + clientMessageY + "y");
		        
		    	String[] nums = clientMessageX.split(" ");
				int numX = Integer.parseInt(nums[0]);
		    	String[] nums2 = clientMessageY.split(" ");
				int numY = Integer.parseInt(nums2[0]);
				
				
		//		int numY = numX*2;
		//		System.out.println(numY); 
				int prox = allPoints.get(10*numY+numX).BombProx(numX,numY);
				System.out.println(prox + " bombs nearby");
				String Responce = prox + "\r\n";
				os.writeBytes(Responce);
	//	        os.writeBytes("a");
		        
		   }
	}
}
