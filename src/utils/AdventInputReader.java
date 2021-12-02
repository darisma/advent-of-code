package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AdventInputReader {
	public Stream<String> getStringStream(String fileName) {
		try {
			return Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
