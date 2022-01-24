package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import classes.Karyawan;

public class Main {
	Scanner in = new Scanner(System.in);
	Random rand = new Random();
	
//	listKaryawan menyimpan semua object Karyawan
	ArrayList<Karyawan> listKaryawan = new ArrayList<Karyawan>();
//	count untuk semua posisi untuk logic bonus salary
	int managerCount = 0, supervisorCount = 0, adminCount = 0;
	
	public void cls() {
		for (int i = 0; i < 21; ++i) {
			System.out.println();
		}
	}
//	======================================== MAIN =====================================================
	public Main() {
		homePage();
	}
//	======================================== MAIN =====================================================
	
	public void homePage() {
		int menu = 0;
		boolean isInteger;
		do {
			System.out.println("LnT Mid Project (BNCC2101093 - Rio Pramana)");
			System.out.println();
			System.out.println("PT Musang");
			System.out.println("1. Insert data karyawan");
			System.out.println("2. View data karyawan");
			System.out.println("3. Update data karyawan");
			System.out.println("4. Delete data karyawan");
			System.out.println("5. Exit");
			do {
				System.out.print("Choose: ");
				isInteger = false;
				try {
					menu = in.nextInt(); in.nextLine();
//					Kalau ga ada exception set booleannya menjadi true
					isInteger = true;
				} catch (Exception e) {
					System.out.println("Make sure you are only entering integer as the input");
					in.next();
				}
			} while (!isInteger);
			switch(menu) {
				case 1:
					insertDataKaryawan();
					break;
				case 2:
					viewDataKaryawan();
					System.out.println("ENTER to return");
					in.nextLine();
					break;
				case 3:
					updateDataKaryawan();
					break;
				case 4:
					deleteDataKaryawan();
					break;
			}
			cls();
		}while(menu != 5);
	}
	
//	Function to check if the randomly generated code is unique
	public boolean isUnique(String code) {
		for (Karyawan karyawan : listKaryawan) {
			if (code.equals(karyawan.getCode())) return false;
		}
		return true;
	}
	
//	Function to randomly generate code
	public String generateKaryawanCode() {
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String number = "0123456789";
		StringBuilder code = new StringBuilder();
//		Generate 2 random uppercase alphabet for the code
		for (int i = 0; i < 2; ++i) {
			code.append(alphabet.charAt(rand.nextInt(alphabet.length())));
		}
		code.append('-');
//		Generate 4 random integer for the code
		for (int i = 0; i < 4; ++i) {
			code.append(number.charAt(rand.nextInt(number.length())));
		}
		
		return code.toString();
	}
	
//	Function to apply bonus salary
	public void applyBonusSalary(String position) {
		double bonusPercentage = 0;
		int index = 0, numberOfUpdatedStaff = 0;
//		Set the bonus percentage according to the position
		if (position.equals("Manager")) {
			bonusPercentage = 0.1;
//			-1 karena countnya udah include karyawan yang sedang di insert
			numberOfUpdatedStaff = managerCount - 1;
		}
		else if (position.equals("Supervisor")) {
			bonusPercentage = 0.075;
			numberOfUpdatedStaff = supervisorCount - 1;
		}
		else if (position.equals("Admin")) {
			bonusPercentage = 0.05;
			numberOfUpdatedStaff = adminCount - 1;
		}
//		Update salary setiap karyawan yang posisinya diberi bonus
		for (Karyawan karyawan : listKaryawan) {
			if (karyawan.getPosition().equals(position)) {
				int addedSalary = (int)((double)karyawan.getSalary() * bonusPercentage);
				karyawan.setSalary(addedSalary + karyawan.getSalary());
//				Print id yang udah di update
//				kalau index = jumlah yang diupdate, println code without comma
//				otherwise, print code (without \n) + comma
				index++;
				if (index == numberOfUpdatedStaff) System.out.println(karyawan.getCode());
				else System.out.print(karyawan.getCode() + ", ");
			}
		}
	}
	
//	Function untuk validasi input name karyawan terdapat minimal 3 alphabet
	public boolean checkThreeAlphabet(String name) {
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcedfghijklmnopqrstuvwxyz";
		int alphabetCount = 0, len = name.length();
		Character temp;
		for (int i = 0; i < len; ++i) {
			temp = name.charAt(i);
			if (alphabet.contains(temp.toString())) alphabetCount++;
		}
		return alphabetCount >= 3 ? true : false;
	}
	
//	Function menu 1
	public void insertDataKaryawan() {
		cls();
		String name, gender, position;
		String code = "";
		int salary = 0, len;
		boolean containsThreeAlphabet;
		
//		take input nama karyawan
		do {
			System.out.print("Input nama karyawan [>= 3]: ");
			name = in.nextLine();
			len = name.length();
			containsThreeAlphabet = checkThreeAlphabet(name);
			if (len < 3) System.out.println("Name must be at least 3 characters long");
			else if (!containsThreeAlphabet) System.out.println("Name must have at least 3 alphabet characters");
		}while(len < 3 || !containsThreeAlphabet);
//		take input gender karyawan
		do {
			System.out.print("Input jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
			gender = in.nextLine();
		}while(!(gender.equals("Laki-laki") || gender.equals("Perempuan")));
//		take input position karyawan
		do {
			System.out.print("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
			position = in.nextLine();
		}while(!(position.equals("Manager") || position.equals("Supervisor") || position.equals("Admin")));
		
//		generate karyawan code
		do {
			code = generateKaryawanCode();
		} while (!isUnique(code));
		System.out.println("Berhasil menambahkan karyawan dengan id " + code);
		
//		set the salary based on the position & add the number of staff on said position
		if (position.equals("Manager")) {
			salary = 8000000;
			managerCount++;
//			check for bonus
			if (managerCount % 3 == 1 && managerCount > 3) {
				System.out.print("Bonus sebesar 10% telah diberikan kepada karyawan dengan id ");
				applyBonusSalary(position);
			}
		}
		else if (position.equals("Supervisor")) {
			salary = 6000000;
			supervisorCount++;
//			check for bonus
			if (supervisorCount % 3 == 1 && supervisorCount > 3) {
				System.out.print("Bonus sebesar 7.5% telah diberikan kepada karyawan dengan id ");
				applyBonusSalary(position);
			}
		}
		else if (position.equals("Admin")) {
			salary = 4000000;
			adminCount++;
//			check for bonus
			if (adminCount % 3 == 1 && adminCount > 3) {
				System.out.print("Bonus sebesar 5% telah diberikan kepada karyawan dengan id ");
				applyBonusSalary(position);
			}
		}
		
//		Add object to the arraylist
		listKaryawan.add(new Karyawan(code, name, gender, position, salary));
		System.out.println("ENTER to return");
		in.nextLine();
	}
	
//	Function menu 2
	public void viewDataKaryawan() {
		cls();
		if (listKaryawan.size() > 0) {
			int index = 0;
//			Create a new arrayList containing every object from listKaryawan to avoid sorting listKaryawan in place
			ArrayList<Karyawan> copyListKaryawan = new ArrayList<Karyawan>(listKaryawan);
//			This arrayList is only used for printing every karyawan's details with sorted name
			
//			Sort the new arrayList by the karyawan's name
//			Using built-in method from Java-8 to generate a Comparator to compare karyawan's name for sorting
			copyListKaryawan.sort(Comparator.comparing(Karyawan::getName));
			
//			Print table
			System.out.println("|----|-----------------|------------------------------|---------------|--------------|-------------|");
			System.out.printf("|%4s|%17s|%30s|%15s|%14s|%13s|\n", "No", "Kode Karyawan", "Nama Karyawan", "Jenis Kelamin", "Jabatan", "Gaji Karyawan");
			System.out.println("|----|-----------------|------------------------------|---------------|--------------|-------------|");
			for (Karyawan karyawan : copyListKaryawan) {
				index++;
				System.out.printf("|%4s|%17s|%30s|%15s|%14s|%13s|\n", index, karyawan.getCode(), karyawan.getName(), karyawan.getGender(), karyawan.getPosition(), karyawan.getSalary());
			}
			System.out.println("|----|-----------------|------------------------------|---------------|--------------|-------------|");
		}
		else {
//			Kalau tidak ada data karyawan
			System.out.println("There are no karyawan yet");
		}
	}
	
//	Function menu 3
	public void updateDataKaryawan() {
		viewDataKaryawan();
		if (listKaryawan.size() > 0) {
//			Duplicate arraylistnya lagi buat dapetin object yang mau diupdate sesuai input nomor urutan
			ArrayList<Karyawan> copyListKaryawan = new ArrayList<Karyawan>(listKaryawan);
			copyListKaryawan.sort(Comparator.comparing(Karyawan::getName));
			
			String name, gender, position, prevPosition = "";
			int salary = 0, staffCount = copyListKaryawan.size(), len, nomorUrutan = 0;
			boolean containsThreeAlphabet, positionChanged = false, isInteger;
			Karyawan updatedStaff;

//			Take input nomor urutan with validasi inputnya integer
			do {
				do {
					System.out.print("Input nomor urutan karyawan yang ingin diupdate: ");
					isInteger = false;
					try {
						nomorUrutan = in.nextInt(); in.nextLine();
//						Kalau ga ada exception set booleannya menjadi true
						isInteger = true;
					} catch (Exception e) {
						System.out.println("Make sure you are only entering integer as the input");
						in.next();
					}
				} while (!isInteger);
				if (nomorUrutan <= 0 || nomorUrutan > staffCount) System.out.println("Input must between 1 and " + staffCount);
			} while (nomorUrutan <= 0 || nomorUrutan > staffCount);
			
//			Set variable menjadi object yang ingin diupdate
			updatedStaff = copyListKaryawan.get(nomorUrutan - 1);
			
//			Take input nama karyawan
			do {
				System.out.print("Input nama karyawan [>= 3]: ");
				name = in.nextLine();
//				Jika input = 0, skip do while ini
				if (name.equals("0")) break;
				len = name.length();
				containsThreeAlphabet = checkThreeAlphabet(name);
				if (len < 3) System.out.println("Name must be at least 3 characters long");
				else if (!containsThreeAlphabet) System.out.println("Name must have at least 3 alphabet characters");
			}while(len < 3 || !containsThreeAlphabet);
//			If the name is updated, then update the name of the karyawan
			if (!name.equals("0")) updatedStaff.setName(name);
			
//			Take input gender karyawan
			do {
				System.out.print("Input jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
				gender = in.nextLine();
//				Jika input = 0, skip do while ini
				if (gender.equals("0")) break;
			}while(!(gender.equals("Laki-laki") || gender.equals("Perempuan")));
//			If the gender is updated, then update the gender of the karyawan
			if (!gender.equals("0")) updatedStaff.setGender(gender);
			
//			Take input position karyawan
			do {
				System.out.print("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
				position = in.nextLine();
//				Jika input = 0, skip do while ini
				if (position.equals("0")) break;
			}while(!(position.equals("Manager") || position.equals("Supervisor") || position.equals("Admin")));
//			If the position is updated, then update the position of the karyawan
			if (!position.equals("0")) {
//				Check if the position is changed from the previous one
				positionChanged = position.equals(updatedStaff.getPosition()) ? false : true;
//				Store the previous position
				prevPosition = updatedStaff.getPosition();
//				Update the position
				updatedStaff.setPosition(position);
			}
			System.out.println("Berhasil mengupdate karyawan dengan id " + updatedStaff.getCode());
			
//			set the salary based on the position & add the number of staff on said position if position is changed
//			Jalankan hanya kalau positionnya diupdate (berubah dari position sebelumnya)
			if (positionChanged) {
//				Kurangin dulu count position sebelumnya
				if (prevPosition.equals("Manager")) managerCount--;
				else if (prevPosition.equals("Supervisor")) supervisorCount--;
				else if (prevPosition.equals("Admin")) adminCount--;
//				Baru update count position baru (bonus jika ada)
				if (position.equals("Manager")) {
					salary = 8000000;
					managerCount++;
//					check for bonus
					if (managerCount % 3 == 1 && managerCount > 3) {
						System.out.print("Bonus sebesar 10% telah diberikan kepada karyawan dengan id ");
						applyBonusSalary(position);
					}
				}
				else if (position.equals("Supervisor")) {
					salary = 6000000;
					supervisorCount++;
//					check for bonus
					if (supervisorCount % 3 == 1 && supervisorCount > 3) {
						System.out.print("Bonus sebesar 7.5% telah diberikan kepada karyawan dengan id ");
						applyBonusSalary(position);
					}
				}
				else if (position.equals("Admin")) {
					salary = 4000000;
					adminCount++;
//					check for bonus
					if (adminCount % 3 == 1 && adminCount > 3) {
						System.out.print("Bonus sebesar 5% telah diberikan kepada karyawan dengan id ");
						applyBonusSalary(position);
					}
				}
//				Update salary baru
				updatedStaff.setSalary(salary);
			}
		}
		System.out.println("ENTER to return");
		in.nextLine();
	}
	
//	Function menu 4
	public void deleteDataKaryawan() {
		viewDataKaryawan();
		if (listKaryawan.size() > 0) {
//			Duplicate arraylistnya lagi buat dapetin object yang mau diupdate sesuai input nomor urutan
			ArrayList<Karyawan> copyListKaryawan = new ArrayList<Karyawan>(listKaryawan);
			copyListKaryawan.sort(Comparator.comparing(Karyawan::getName));
			
			int staffCount = copyListKaryawan.size(), nomorUrutan = 0;
			boolean isInteger;
			Karyawan deletedStaff;
			
//			Take input nomor urutan w/ validasi input = integer
			do {
				do {
					System.out.print("Input nomor urutan karyawan yang ingin dihapus: ");
					isInteger = false;
					try {
						nomorUrutan = in.nextInt(); in.nextLine();
//						Kalau ga ada exception set booleannya menjadi true
						isInteger = true;
					} catch (Exception e) {
						System.out.println("Make sure you are only entering integer as the input");
						in.next();
					}
				} while (!isInteger);
				if (nomorUrutan <= 0 || nomorUrutan > staffCount) System.out.println("Input must between 1 and " + staffCount);
			} while (nomorUrutan <= 0 || nomorUrutan > staffCount);
//			Set variable menjadi object yang ingin didelete
			deletedStaff = copyListKaryawan.get(nomorUrutan - 1);
			
//			Delete the object from listKaryawan
//			Using .remove(index) instead of .remove(object) to be safe because listKaryawan contains User-Defined DataType
			String codeToBeDeleted = deletedStaff.getCode();
			for(int i = 0; i < staffCount; ++i) {
				if (codeToBeDeleted.equals(listKaryawan.get(i).getCode())) {
//					Kurangin dulu count posisi karyawan tersebut
					String position = listKaryawan.get(i).getPosition();
					if (position.equals("Manager")) managerCount--;
					else if (position.equals("Supervisor")) supervisorCount--;
					else if (position.equals("Admin")) adminCount--;
//					Baru remove karyawannya
					listKaryawan.remove(i);
					System.out.println("Karyawan dengan kode " + codeToBeDeleted + " berhasil dihapus");
					break;
				}
			}
		}
		System.out.println("ENTER to return");
		in.nextLine();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
