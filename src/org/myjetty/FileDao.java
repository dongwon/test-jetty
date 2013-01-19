package org.myjetty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FileDao {

	UserVo vo = new UserVo();
	File rf;
	File wf;
	FileWriter fw;
	BufferedWriter bw;
	FileReader fr;
	BufferedReader br;

	public void getReader(String fileName) {
		rf = new File(fileName);
		try {
			fr = new FileReader(rf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
	}

	public void getWriter(String fileName){
		wf = new File(fileName);
		try {
			fw = new FileWriter(wf, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
	}

	public void insert(UserVo vo) throws IOException{
		getWriter("UserList.txt");
		String record = vo.getName() + "," + vo.getSex() + "," + vo.getAge() + "," + vo.getPhoneNumber();
		bw.write(record);
		bw.flush();
		bw.newLine();
		bw.flush();
		bw.close();
	}

	public void delete(String name) throws IOException{

		getReader("UserList.txt");
		getWriter("UserList.tmp");
		String str;
		ArrayList<String> al = new ArrayList<String>();

		while ((str = br.readLine()) != null) {
			al.add(str);
		}

		for (Iterator<String> iterator = al.iterator(); iterator.hasNext();) {
			Scanner scan = new Scanner(iterator.next());
			scan.useDelimiter("\\s*,\\s*");
			vo.setName(scan.next());
			vo.setSex(scan.nextBoolean());
			vo.setAge(scan.nextInt());
			vo.setPhoneNumber(scan.next());
			if(name.equals(vo.getName())) {
				System.out.println("match");
				continue;
			}
			bw.write(vo.getName() + "," + vo.getSex() + "," + vo.getAge() + "," + vo.getPhoneNumber());
			wf.renameTo(rf);
		}
		br.close();
		bw.close();
	}

	public void update(UserVo vo) throws IOException{


	}

	public UserVo select(String name) throws IOException{

		ArrayList<String> al = new ArrayList<String>();
		String str;
		getReader("UserList.txt");

		while ((str = br.readLine()) != null) {
			al.add(str);
		}

		for (Iterator<String> iterator = al.iterator(); iterator.hasNext();) {
			Scanner scan = new Scanner(iterator.next());
			scan.useDelimiter("\\s*,\\s*");
			vo.setName(scan.next());
			vo.setSex(scan.nextBoolean());
			vo.setAge(scan.nextInt());
			vo.setPhoneNumber(scan.next());
			if(name.equals(vo.getName())){
				break;
			}
		}
		br.close();
		return vo;
	}
}
