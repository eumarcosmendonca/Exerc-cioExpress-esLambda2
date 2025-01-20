package Program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import Entities.Product;

public class Main {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> list = new ArrayList<>();

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			
			String line = bufferedReader.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				if (fields.length < 2) {
					System.out.println("Invalid line format: " + line);
				}
				else {
					String name = fields[0];
					try {
						Double price = Double.parseDouble(fields[1]);
						list.add(new Product(name, price));
					}
					catch (NumberFormatException e) {
						System.out.println("Invalid price value: " + fields[1]);
					}
				}
				line = bufferedReader.readLine();
			}

			if (list.isEmpty()) {
				System.out.println("The file is empty or contains no valid data.");
				return;
			}

			double sum = list.stream().mapToDouble(Product::getPrice).sum();
			double average = sum / list.size();

			list.sort((x1, x2) -> -x1.getName().compareTo(x2.getName()));

			List<Product> newList = list.stream().filter(x -> x.getPrice() < average).collect(Collectors.toList());
			
			System.out.println("Average price R$ " + String.format("%.2f", average));
			for (Product obj : newList) {
				System.out.println(obj.getName());
			}

		}
		catch (IOException msg) {
			System.out.println("Error: " + msg.getMessage());
		}

		sc.close();
		
	}
	
}
