import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Tester {
	public static void main(String[]args) throws IOException {

		final int THRESHOLD = 40;

		final String FILEPATH1 = "/Users/jerryhuang/Downloads/31.jpg";
		final String FILEPATH2 = "/Users/jerryhuang/Downloads/32.jpg";

		FingerprintTemplate probe = new FingerprintTemplate(
				new FingerprintImage(
						Files.readAllBytes(Paths.get(FILEPATH1)),
						new FingerprintImageOptions()
								.dpi(500)));
		FingerprintTemplate candidate = new FingerprintTemplate(
				new FingerprintImage(
						Files.readAllBytes(Paths.get(FILEPATH2)),
						new FingerprintImageOptions()
								.dpi(500)));


		double score = new FingerprintMatcher(probe)
				.match(candidate);
		System.out.println("Score: " + score);
		boolean matches = score >= 40;

//		System.out.println("Loading files...");
//		File folder = new File("/Users/jerryhuang/Documents/SourceAFIS/FingerprintMachineLearningProject");
//		File[] listOfFiles = folder.listFiles();
//		String[] files = new String[listOfFiles.length];
//		int a = 0;
//		for (File f : listOfFiles) {
//			files[a] = f.getName();
//			a++;
//		}
//		System.out.println(files.length + " total files...");
//
//		System.out.println("Sorting filenames alphabetically...");
//		String temp = "";
//		for (int i = 0; i < files.length; i++)
//			for (int j = i + 1; j < files.length; j++)
//				if (files[i].compareTo(files[j]) > 0)
//				{
//					temp = files[i];
//					files[i] = files[j];
//					files[j] = temp;
//				}
//
//		List<String> fileNamesAsList = new ArrayList<>();
//		for (String str : files)
//			fileNamesAsList.add(str);
//		fileNamesAsList.remove(0);
//
//		System.out.println("Creating templates...");
//		List<FingerprintTemplate> templates = new ArrayList<>();
//		for (int i = 1; i < files.length; i++) {
//			FingerprintTemplate template_temp = new FingerprintTemplate(
//					new FingerprintImage(
//							Files.readAllBytes(Paths.get(FILEPATH + files[i])),
//							new FingerprintImageOptions()
//									.dpi(600)));
//			templates.add(template_temp);
//		}

	}
}
