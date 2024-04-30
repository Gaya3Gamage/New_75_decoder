package com.decoder.decoder;

import com.decoder.decoder.Configuration.AppConfig;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@EnableScheduling
@Configuration
@ComponentScan
@EntityScan
@EnableJpaRepositories
@SpringBootApplication
public class Application {

	private final MessageDecoder decoder;

	@Autowired
	public Application(MessageDecoder decoder) throws IOException {
		this.decoder = decoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct

	public void runDecoder() {
// for manually input data
		String[] messages = {
				"@LEN:0149;STB:00001;TM:14/02/2023,13:35:00;V:3.45;D:1;T:06;C:04;P01:-99999999|-99999999|-99999999|-99999999|000014834|000014834;K01:99|99|99|99|00|00;4C#",
				"@LEN:0084;STA:00001;TM:10/02/2023,11:15:25;V:3.38;D:2;T:01;C:01;P01:000010001;K01:00;99#",
				"@LEN:0084;STA:00001;TM:10/02/2023,11:16:25;V:3.38;D:2;T:01;C:02;P01:000010001;K01:00;9B#",
				"@LEN:0084;STA:00001;TM:10/02/2023,11:17:25;V:3.38;D:2;T:01;C:03;P01:000010001;K01:00;9D#",
				"@LEN:0084;STA:00001;TM:10/02/2023,11:18:25;V:3.38;D:2;T:01;C:04;P01:000010001;K01:00;9F#",
				"@LEN:0084;STA:00001;TM:10/02/2023,11:19:25;V:3.38;D:2;T:01;C:05;P01:000010001;K01:00;A1#",
				"@LEN:0084;STA:00001;TM:10/02/2023,11:20:25;V:3.38;D:2;T:01;C:06;P01:000010001;K01:00;9A#",
				"@LEN:0149;STB:00001;TM:14/02/2023,13:35:00;V:3.45;D:1;T:06;C:04;P01:-99999999|-99999999|-99999999|-99999999|000014834|000014834;K01:99|99|99|99|00|00;4C#",
				"@LEN:0267;STB:00001;TM:16/03/2023,18:30:00;V:7.30;D:3;T:04;C:95;A02:00000|00000|00000|00000;A04:7.304|7.306|7.307|7.307;P01:000001058|000001058|000001058|000001058;P02:000000007|000000007|000000007|000000007;P03:000001058|000001058|000001058|000001058;K01:00|00|00;3E#"

		};


		for (String message : messages) {
			decoder.decode(message);
		}
//// For the 1st 150 data of songmao_source file data
//		try {
//			// Read all lines from the file named "SourceData.txt"
//			// Change the file path as per your actual file location
//			String filePath = "C:\\Users\\Gflow User 5\\Desktop\\work\\my project\\75decoder\\src\\main\\resources\\SourceData2.txt";
//			String[] messages = Files.readAllLines(Paths.get(filePath)).toArray(new String[0]);
//
//
//
//			// Call decode method of the Test bean for each message
//			for (String message : messages) {
//				decoder.decode(message);
//			}
//		} catch (IOException e) {
//			// Handle any IO exceptions such as file not found
//			e.printStackTrace();
//		}
//// For all songmao_source file data
//
//			try {
//				// Read all lines from the file
//				String filePath = "C:\\Users\\Gflow User 5\\Desktop\\work\\my project\\75decoder\\src\\main\\resources\\songmao_source";
//				List<String> lines = Files.readAllLines(Paths.get(filePath));
//
//				// Iterate over each line
//				for (String line : lines) {
//					// Extract the message within double quotation marks
//					String message = extractMessage(line);
//
//					// Pass the extracted message to the decode method
//					decoder.decode(message);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		// Method to extract message within double quotation marks
//		private String extractMessage(String line) {
//			int startIndex = line.indexOf('"');
//			int endIndex = line.lastIndexOf('"');
//			if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
//				return line.substring(startIndex + 1, endIndex);
//			} else {
//				return "";
//			}
//		}
////for input data from port 1725
//		int port = 1725;
//
//		try (ServerSocket serverSocket = new ServerSocket(port)) {
//			System.out.println("Waiting for incoming connections on port " + port + "...");
//
//			while (true) {
//				// Accept incoming connections
//				Socket clientSocket = serverSocket.accept();
//
//				// Read data from the client socket
//				try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//					String message;
//					while ((message = in.readLine()) != null) {
//						// Process the received message
//						System.out.println("Received message: " + message);
//						decoder.decode(message);
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally {
//					// Close the client socket
//					clientSocket.close();
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}




