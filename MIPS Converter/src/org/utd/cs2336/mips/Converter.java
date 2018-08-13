/**
 * UTD CS 2336
 * Assignment 4 - Spring 2016
 * @author Parijat Singh
 * email: parijat.sigh@gmail.com * 
 */

package org.utd.cs2336.mips;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Converter {
	
	
	/**
	 * Array of Register names. The array is arranged in ascending order.
	 * The index of the register is it's int value. To convert to the corresponding binary representation
	 * the index can be parsed using Integer class using radix 2 and then by adding leading zeros to make
	 * it 5 character long.
	 */
	private String[] regNameArray = {"$zero", "$at", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3", 
                                       "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7", 
                                       "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7", 
                                       "$t8", "$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra"};

	
	ArrayList<MipsInstruction> mList = new ArrayList<MipsInstruction>();
	
	/**
	 * Reads MIPS Information sheet into instruction list
	 */
	public void readTable(){
		try {
			   File file = new File("mips.csv");
			   Scanner scanner = new Scanner(file);
			   scanner.useDelimiter("\n");
			   while (scanner.hasNext()) {
			    String line = scanner.nextLine();
			    String[] parts = line.split(",");
			    MipsInstruction mi = new MipsInstruction();
			    mList.add(mi);
			    mi.instrName = parts[0];
			    mi.description = parts[1];
			    mi.instrType = parts[2];
			    mi.opcode = Integer.parseInt(parts[3].replace("0x", ""),16);
			    if(parts[4] != ""){
			    	mi.functioncode = Integer.parseInt(parts[4].replace("0x", ""),16);
			    }
			    mi.detail = parts[5];
			    mi.args = parts[6].split(";");
			    System.out.println(mi.instrName);
			   }
			   scanner.close();
			  } catch (FileNotFoundException e) {
			   e.printStackTrace();
			  } 
	}
	
	public boolean isValidRegister(String reg){
		if(!reg.startsWith("$")) reg = "$"+reg;
		for (int i = 0; i < regNameArray.length; i++) {
			if(reg.equals(regNameArray[i])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Fetches the corresponding register's binary format. Pads with leading zeros
	 * @param reg
	 * @return
	 */
	public String getRegisterBinary(String reg){
		if(!reg.startsWith("$")) reg = "$"+reg;
		for (int i = 0; i < regNameArray.length; i++) {
			if(reg.equals(regNameArray[i])){
				return Helper.intToBinary(i, 5);
			}
		}
		return null;
	}
	
	public String getRegisterName(String binary){
		int i = Integer.parseInt(binary, 2);
		return regNameArray[i];
	}
	
	/*public String mipsToHexDetails(String instr){
		String binary = mipsToBinary(instr);
		return Helper.binaryToHex(binary, 8);
	}*/
	
	/*public String mipsToHex(String instr){
		String binary = mipsToBinary(instr);
		return Helper.binaryToHex(binary, 8);
	}*/
	
	public InstructionData mipsToHex(String instr) throws MIPSFormatException{
		InstructionData insData = new InstructionData();
		insData.instrForm = instr;
		String binary = "";
		instr = instr.replaceAll(",", " ");
		instr = instr.replaceAll("\\(", " ");
		instr = instr.replaceAll("\\)", " ");
		Scanner sc = new Scanner(instr);
		String instrName = sc.next();
		String nxt = null;
		MipsInstruction mi = getByName(instrName);
		insData.mi = mi;
		for(int i=0; i<mi.args.length; i++){
			if("rs".equals(mi.args[i])){
				insData.rs = sc.next();
			}else if("rt".equals(mi.args[i])){
				insData.rt = sc.next();
			}else if("rd".equals(mi.args[i])){
				insData.rd = sc.next();
			}else if("shamt".equals(mi.args[i])){
				insData.shamt = sc.next();
			}else if("imm".equals(mi.args[i])){
				insData.imm = sc.next();
			}else if("addr".equals(mi.args[i])){
				insData.addr = sc.next();
			}
		}
		sc.close();
		convertToBinaryString(insData);
		insData.hexForm =  Helper.binaryToHex(insData.binaryForm, 8);
		return insData;
	}	
	
	public void convertToBinaryString(InstructionData insData){
		MipsInstruction mi = insData.mi;
		String binaryStr = "";
		if("R".equals(mi.instrType)){
			binaryStr += Helper.intToBinary(mi.opcode, 6);
			if(insData.rs == null) binaryStr += "00000";
			else binaryStr += getRegisterBinary(insData.rs);
			if(insData.rt == null) binaryStr += "00000";
			else binaryStr += getRegisterBinary(insData.rt);
			if(insData.rd == null) binaryStr += "00000";
			else binaryStr += getRegisterBinary(insData.rd);
			if(insData.shamt == null) binaryStr += "00000";
			else binaryStr += Helper.hexToBinary(insData.shamt, 5);
			binaryStr += Helper.intToBinary(mi.functioncode, 6);
			//Helper.
		}
		if("I".equals(mi.instrType)){
			binaryStr += Helper.intToBinary(mi.opcode, 6);
			if(insData.rs == null) binaryStr += "00000";
			else binaryStr += getRegisterBinary(insData.rs);
			if(insData.rt == null) binaryStr += "00000";
			else binaryStr += getRegisterBinary(insData.rt);
			if(insData.imm == null) binaryStr += "00000";
			else binaryStr += Helper.hexToBinary(insData.imm, 16);
			//Helper.
		}
		if("J".equals(mi.instrType)){
			binaryStr += Helper.intToBinary(mi.opcode, 6);
			if(insData.addr == null) binaryStr += Helper.hexToBinary("0x00", 26);
			else binaryStr += Helper.hexToBinary(insData.addr, 26);
			//Helper.
		}
		insData.binaryForm = binaryStr;
		//return binaryStr;
	}
	
	public int getPosition(String r){
		for (int i = 0; i < regNameArray.length; i++) {
			if(regNameArray[i].equals(r))
				return i;
		}
		return 0;
	}
	
	public MipsInstruction getByName(String name){
		for (MipsInstruction mipsInstruction : mList) {
			if(mipsInstruction.instrName.equalsIgnoreCase(name))
				return mipsInstruction;
		}
		return null;
	}	
	
	/**
	 * Finds a MIP instruction from the list by comparing operation code
	 * @param opcode
	 * @return
	 */
	public MipsInstruction getByOpcode(int opcode){
		for (MipsInstruction mipsInstruction : mList) {
			if(mipsInstruction.opcode == opcode)
				return mipsInstruction;
		}
		return null;
	}	
	
	/**
	 * Finds a MIP instruction from the list by comparing function code
	 * @param functioncode
	 * @return
	 */
	public MipsInstruction getByFunctioncode(int functioncode){
		for (MipsInstruction mipsInstruction : mList) {
			if(mipsInstruction.functioncode == functioncode)
				return mipsInstruction;
		}
		return null;
	}
	
	public InstructionData hexToMips(String hex) throws MIPSFormatException{
		hex = hex.trim();
		String binary = Helper.hexToBinary(hex, 32);
		System.out.println(binary);
		MipsInstruction mi = null;
		//Lets find the opCode
		String opCodeBinary = binary.substring(0,6);
		int opcode = Integer.parseInt(opCodeBinary, 2);
		//If opcode is 0 then get the function code
		if(opcode == 0){
			String functionBinary = binary.substring(26,32);
			int functioncode = Integer.parseInt(functionBinary, 2);
			mi = getByFunctioncode(functioncode);
		}else{
			mi = getByOpcode(opcode);
		}
		if(mi == null) {
			throw new MIPSFormatException("Instruction not found.");
		}
		InstructionData insData = new InstructionData();
		insData.mi = mi;
		if("R".equals(mi.instrType)){
			insData.rs = getRegisterName(binary.substring(6,11));
			insData.rt = getRegisterName(binary.substring(11,16));
			insData.rd = getRegisterName(binary.substring(16,21));
			insData.shamt = binary.substring(21,26);
			insData.shamt = Helper.binaryToHex(insData.shamt, 0);			
		}
		if("I".equals(mi.instrType)){
			insData.rs = getRegisterName(binary.substring(6,11));
			insData.rt = getRegisterName(binary.substring(11,16));
			insData.imm = binary.substring(16,32);
			insData.imm = Helper.binaryToHex(insData.imm, 0);
		}
		if("J".equals(mi.instrType)){
			insData.addr = binary.substring(6,32);
			insData.addr = Helper.binaryToHex(insData.addr, 0);			
		}
		String instrStr = mi.instrName;
		for(int i=0; i<mi.args.length; i++){
			if("rs".equals(mi.args[i])){
				instrStr += " " + insData.rs;
			}else if("rt".equals(mi.args[i])){
				instrStr += " " + insData.rt;
			}else if("rd".equals(mi.args[i])){
				instrStr += " " + insData.rd;
			}else if("shamt".equals(mi.args[i])){
				instrStr += " " + insData.shamt;
			}else if("imm".equals(mi.args[i])){
				instrStr += " " + insData.imm;
			}else if("addr".equals(mi.args[i])){
				instrStr += " " + insData.addr;
			}
		}
		insData.binaryForm = binary;
		insData.hexForm = hex;
		insData.instrForm = instrStr;
		return insData;
	}
	
	public String hexToMipsDetails(String hex) throws Exception{
		InstructionData insData = hexToMips(hex);
		MipsInstruction mi = insData.mi;
		String res = "";
		res = res + "Instruction\t" + insData.instrForm + "\n";
		res = res + "Hexadecimal\t" + insData.hexForm + "\n";
		res = res + "Binary     \t" + insData.binaryForm + "\n\n";
		res = res + insData.mi.description + "\n";
		res = res + insData.mi.detail + "\n";
		return res;
	}
	
	/*public String print(InstructionData insData){
		MipsInstruction mi = insData.mi;
		String instrStr = mi.instrName;
		for(int i=0; i<mi.args.length; i++){
			if("rs".equals(mi.args[i])){
				instrStr += " " + insData.rs;
			}else if("rt".equals(mi.args[i])){
				instrStr += " " + insData.rt;
			}else if("rd".equals(mi.args[i])){
				instrStr += " " + insData.rd;
			}else if("shamt".equals(mi.args[i])){
				instrStr += " " + insData.shamt;
			}else if("imm".equals(mi.args[i])){
				instrStr += " " + insData.imm;
			}else if("addr".equals(mi.args[i])){
				instrStr += " " + insData.addr;
			}
		}		
		return instrStr;
	}*/

	

}
