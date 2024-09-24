package com.vintech.salary_management.VinTechCompany;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VinTechCompanyApplication {
	private static final Logger logger = LoggerFactory.getLogger(VinTechCompanyApplication.class);

	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_YELLOW = "\u001B[33m";

	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		setConsoleCodePage();
		clearConsole();

		String border = ANSI_CYAN + "----------------------------------------" + ANSI_RESET;
		String emptyLine = ANSI_CYAN + "|                                      |" + ANSI_RESET;
		String message = "Đang khởi động chương trình...";
		int paddingSize = (38 - message.length()) / 2;
		String padding = " ".repeat(paddingSize);
		String messageLine = ANSI_CYAN + "|" + padding + ANSI_GREEN + message + ANSI_CYAN + padding + "|"
				+ ANSI_RESET;

		if (message.length() % 2 != 0) {
			messageLine = ANSI_CYAN + "|" + padding + ANSI_GREEN + message + ANSI_CYAN + padding + "|" + ANSI_RESET;
		}

		System.out.println(border);
		System.out.println(emptyLine);
		System.out.println(messageLine);
		System.out.println(emptyLine);
		System.out.println(border);

		SpringApplication.run(VinTechCompanyApplication.class, args);
		System.out.println(ANSI_YELLOW + "\nServer đang chạy trên " + ANSI_CYAN + "http://localhost:8080" + ANSI_RESET);
	}

	private static void setConsoleCodePage() {
		if (System.getProperty("os.name").contains("Windows")) {
			try {
				new ProcessBuilder("cmd", "/c", "chcp", "65001").inheritIO().start().waitFor();
			} catch (IOException | InterruptedException e) {
				logger.warn(e.getMessage());
			}
		}
	}

	private static void clearConsole() {
		try {
			final String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				new ProcessBuilder("clear").inheritIO().start().waitFor();
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (IOException | InterruptedException e) {
			logger.warn(e.getMessage());
		}
	}
}
