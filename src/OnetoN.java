import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Supports 1 to N matching. For each fingerprint in the folder, it finds all matching fingerprints in the database.
 */
public class OnetoN {
	public static void main(String[]args) throws IOException {

		final int THRESHOLD = 40;
		final String FILEPATH = "/Users/jerryhuang/Documents/SourceAFIS/FingerprintMachineLearningProject/";

		String f1 = FILEPATH + "A1158_index1" + ".jpg";

		System.out.println("Loading files...");
		File folder = new File("/Users/jerryhuang/Documents/SourceAFIS/FingerprintMachineLearningProject");
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
		int count = files.length;
		for (int i = 0; i < count; i++)
		{
			for (int j = i + 1; j < count; j++)
			{
				if (files[i].compareTo(files[j]) > 0)
				{
					temp = files[i];
					files[i] = files[j];
					files[j] = temp;
				}
			}
		}

		System.out.println("Creating templates...");
		List<FingerprintTemplate> templates = new ArrayList<>();
		for (int i = 1; i < files.length; i++) {
			FingerprintTemplate probe = new FingerprintTemplate(
					new FingerprintImage(
							Files.readAllBytes(Paths.get(FILEPATH + files[i])),
							new FingerprintImageOptions()
									.dpi(600)));
			templates.add(probe);
		}

		System.out.println("Running matches");
		for (int i = 0; i < templates.size(); i++) {
			int num_of_matches = 0;
			FingerprintTemplate probe_template = templates.get(i);

			List<String>listOfMatches = new ArrayList<>();

			for (int j = 0; j < templates.size(); j++) {
				double score = new FingerprintMatcher(templates.get(j))
						.match(probe_template);

//				System.out.println(" Score: " + score);

				boolean matches = score >= THRESHOLD;
				if (matches) {
//					System.out.println(files[j+1]);
					listOfMatches.add(files[j+1]);
					num_of_matches++;
				}
			}

			int true_match = 0;
			int false_match = 0;
			for (String str : listOfMatches) {
				if (files[i+1].substring(0, files[i+1].length() - 5).equals(str.substring(0, str.length() - 5))) {
					true_match++;
				} else {
					false_match++;
				}
			}
			System.out.println(i + " Total matches: " + num_of_matches + " Input: " + files[i+1] + " True Match: "
					+ true_match + " False Match: " + false_match);
		}
	}
}
