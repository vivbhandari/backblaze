package backblaze.assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
	private double average;
	private List<Double> numbers = new ArrayList<Double>();

	public InputReader(String path) {
		String line;
		BufferedReader br = null;
		double sum = 0;
		try {
			File file = new File(path);
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				try {
					double tmp = Double.parseDouble(line.trim());
					numbers.add(tmp);
					sum += tmp;
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		average = sum / numbers.size();
		System.out.println("total = " + numbers.size());
		System.out.println("average = " + average);
	}

	public double getAverage() {
		return average;
	}

	public List<Double> getNumbers() {
		return numbers;
	}
}
