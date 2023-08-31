/* Hoàng Thị Thanh Phương - K204061445 - Leader
 * Nguyễn Thị Huyền Thương - K204061450
 * Nguyễn Trần Thúy Quỳnh - K204060307
 * Nguyễn Hoàng Tính - K204061451
 */
package ca.pfv.spmf.test;

/* This file is copyright (c) 2021 Philippe Fournier-Viger
* 
* This file is part of the SPMF DATA MINING SOFTWARE
* (http://www.philippe-fournier-viger.com/spmf).
* 
* SPMF is free software: you can redistribute it and/or modify it under the
* terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* SPMF is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
* A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* You should have received a copy of the GNU General Public License along with
* SPMF. If not, see <http://www.gnu.org/licenses/>.
*/

import java.io.UnsupportedEncodingException; 
import java.net.URL;
import java.util.Arrays;
import ca.pfv.spmf.algorithms.classifiers.data.Instance;
import ca.pfv.spmf.algorithms.classifiers.data.CSVDataset;
import ca.pfv.spmf.algorithms.classifiers.knn.AlgoKNN;
import ca.pfv.spmf.algorithms.classifiers.knn.ClassifierKNN;


/**
 * Example of how to run the KNN algorithm
 * 
 * @author Philippe Fournier-Viger, 2021
 *
 */
public class ChurnKNN_prediction {

	public static void main(String[] args) throws Exception {

		// ********************************************************
		// **************** READ A DATASET IN MEMORY ************
		// ********************************************************
		System.out.println("========= Step 1: Read the dataset in memory ==========");

		// We choose "play" as the target attribute that we want to predict using the
		// other attributes
		String targetClassName = "Exited";

		// Load the dataset in memory.
		// If the dataset is in SPMF format:
		String datasetPath = fileToPath("bankData.csv");
		CSVDataset dataset = new CSVDataset(datasetPath, targetClassName);
		
		// Use the following line to see statistics about the dataset
		dataset.printStats();

		// For debugging we could print the dataset as it is loaded in memory:
//		dataset.printInternalRepresentation();
//		dataset.printStringRepresentation();

		// ********************************************************
		// **************** TRAIN THE MODEL (classifier) **********
		// ********************************************************
		System.out.println();
		System.out
				.println("==== Step 2: Train the model and run automated classification experiments on the dataset===");
		System.out.println();

		// Parameter
		int k = 1;
		
		// Train the model on the training data and make predictions on the testing data
		ClassifierKNN classifier = (ClassifierKNN) new AlgoKNN(k).trainAndCalculateStats(dataset);

		// ****************************************
		// **************** OPTIONAL **************
		// ****************************************
		// If you want to save a trained model so that you can load it into memory
		// later,
		// you can do as follows.
		// First, save the classifier to a file using serialization:
//		System.out.println(" Save the classifier to a file");
//		classifier.saveTrainedClassifierToFile("classifier.ser");
//
//		// Second, you can the classifier into memory:
//		System.out.println(" Read the classifier from a file");
//		classifier = Classifier.loadTrainedClassifierToFile("classifier.ser");
		

		// ********************************************************
		// ***** USE THE MODEL TO MAKE A PREDICTION **********
		// ********************************************************
		//String[] values = { "-1.54714095593388", "0", "1", "-0.946079256", "1.0508202865849", "1", "1" };
		String[][] data = {
			    {"-1.54714095593388", "1", "0", "0.48422460429935", "1.0508202865849", "3", "0"},
			    {"-1.54714095593388", "0", "1", "-0.946079256", "1.0508202865849", "1", "1"}
			};

			for (int i = 0; i < data.length; i++) {
			    String[] values = data[i];
			    System.out.println("Making a prediction for the record:[" + i + "]: " + Arrays.toString(values));

			    Instance instance = dataset.stringToInstance(values);
			    short result = classifier.predict(instance);
			    System.out.println("The predicted value is: " + dataset.getStringCorrespondingToItem(result));
			    System.out.println();
			}

	}

	public static String fileToPath(String filename) throws UnsupportedEncodingException {
		URL url = MainTestKNN_single_prediction.class.getResource(filename);
		return java.net.URLDecoder.decode(url.getPath(), "UTF-8");
	}
}
