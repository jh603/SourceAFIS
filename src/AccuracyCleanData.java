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

/**
 * Generates 785 pairs of positive images and 785 pairs of negative images.
 */
public class AccuracyCleanData {
	public static void main(String[]args) throws IOException {

		final int THRESHOLD = 40;

		final String FILEPATH = "/Users/jerryhuang/Documents/SourceAFIS/png/";

		System.out.println("Loading files...");
		File folder = new File("/Users/jerryhuang/Documents/SourceAFIS/png");
		File[] listOfFiles = folder.listFiles();
		String[] files = new String[listOfFiles.length];
		int a = 0;
		for (File f : listOfFiles) {
			files[a] = f.getName();
			a++;
		}
		System.out.println(files.length + " total files...");

		System.out.println("Sorting filenames alphabetically...");
		String temp = "";
		for (int i = 0; i < files.length; i++)
			for (int j = i + 1; j < files.length; j++)
				if (files[i].compareTo(files[j]) > 0)
				{
					temp = files[i];
					files[i] = files[j];
					files[j] = temp;
				}

		List<String> fileNamesAsList = new ArrayList<>();
		for (String str : files)
			fileNamesAsList.add(str);
		fileNamesAsList.remove(0);

		System.out.println("Creating templates...");
		List<FingerprintTemplate> templates = new ArrayList<>();
		for (int i = 1; i < files.length; i++) {
			FingerprintTemplate template_temp = new FingerprintTemplate(
					new FingerprintImage(
							Files.readAllBytes(Paths.get(FILEPATH + files[i])),
							new FingerprintImageOptions()
									.dpi(600)));
			templates.add(template_temp);
		}

//		Generate Positive Pairs
		System.out.println("Generating Positive Pairs");
		int num_positive_tc = 0;
		int num_positive_matches = 0;

		for (int i = 0; i < templates.size()-1; i++) {
			for (int j = i + 1; j < templates.size(); j++) {

				String file1 = fileNamesAsList.get(i);
				String file2 = fileNamesAsList.get(j);

				if (file1.substring(0, file1.length() - 5).equals(file2.substring(0, file2.length() - 5))) {

//					System.out.println(file1 +  " " + file2);

					num_positive_tc++;
					double score = new FingerprintMatcher(templates.get(i))
							.match(templates.get(j));

//					System.out.println(file1 + " " + file2 + " " + score);
					System.out.println(score);
					boolean matches = score >= THRESHOLD;

					if (matches) num_positive_matches++;
				}
			}
		}

//		Generate Negative Pairs
		System.out.println("Generating Negative Pairs");
		int num_negative_tc = 0;
		int num_negative_matches = 0; // false positive

		while (num_negative_tc < num_positive_tc) {
			int randomIndex1 = ThreadLocalRandom.current().nextInt(0, templates.size());
			int randomIndex2 = ThreadLocalRandom.current().nextInt(0, templates.size());

			String file1 = fileNamesAsList.get(randomIndex1);
			String file2 = fileNamesAsList.get(randomIndex2);

			if (randomIndex1 != randomIndex2 && !file1.substring(0, file1.length() - 5)
					.equals(file2.substring(0, file2.length() - 5))) {

				num_negative_tc++;

				double score = new FingerprintMatcher(templates.get(randomIndex1))
						.match(templates.get(randomIndex2));
				boolean matches = score >= THRESHOLD;

//				System.out.println(randomIndex1 + " " + randomIndex2 + " " + score);

				if (matches) num_negative_matches++;
			}
		}

		System.out.println("Number of positive pairs: " + num_positive_tc);
		System.out.println("Number of true positive: " + num_positive_matches);
		System.out.println("Number of negative pairs: " + num_negative_tc);
		System.out.println("Number of false positives: " + num_negative_matches);
	}
}
