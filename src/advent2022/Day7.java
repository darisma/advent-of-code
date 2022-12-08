package advent2022;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import utils.AdventInputReader;

public class Day7 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day7.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day7().solveAdventA());
		System.out.println("Answer to B is: " + new Day7().solveAdventB());
	}

	private long solveAdventA() {
		
		ElfFile root = createElfFileSystem(INPUT_DATA);
		
		HashMap<String, Long> dirSizes = getDirSizes(root, new HashMap<>());

		return dirSizes.values().stream().filter(e -> e <= 100000 && e > 0).mapToLong(e -> e.longValue()).sum();

	}

	private long solveAdventB() {
		ElfFile root = createElfFileSystem(INPUT_DATA);
		
		HashMap<String, Long> dirSizes = getDirSizes(root, new HashMap<>());
		
		long fileSystemDiskSpace = 70000000L;
		long necessaryDiskSpace = 30000000L;
		                           
	    long inUse = getSize(root.getContent(), Long.valueOf(0));
	    long available = (fileSystemDiskSpace - inUse);
	    
		long amountNeeded = necessaryDiskSpace - available;

		Long amount = dirSizes.values().stream().filter(e -> e > amountNeeded).sorted().findFirst().get();
		
		return amount;
	}
	
	private HashMap<String, Long> getDirSizes(ElfFile root, HashMap<String, Long> allDirSizes) {
		
		for(String key : root.getContent().keySet()) {
			if(root.getContent().get(key).isDirectory()) {
				allDirSizes.put(key, getSize(root.getContent().get(key).getContent(), Long.valueOf(0)));
					getDirSizes(root.getContent().get(key), allDirSizes);
			}
		}
		return allDirSizes;
	}

	private ElfFile createElfFileSystem(List<String> inputData) {

		ElfFile root = new ElfFile(null, "root");
		
		ElfFile current = root;

		for(String row : inputData.subList(1, inputData.size())) {

			if(row.equalsIgnoreCase("$ cd ..")) {
				// set the parent of current as the new current
				if(current.getParent() != null) {
					current = current.getParent();
				}
			}else if (row.startsWith("$ cd ")){
				String directoryToGoTo = row.split(" ")[2];
				if(current.getContent().get(current.getName() + "/" + directoryToGoTo) == null) {
					current.getContent().put(current.getName() + "/" + directoryToGoTo, new ElfFile(current, directoryToGoTo));
				}
				current = current.getContent().get(current.getName() + "/" + directoryToGoTo);	
				
			}else if (row.startsWith("dir ")) {
				String directoryFound = row.split(" ")[1];
				if(current.getContent().get(current.getName() + "/" + directoryFound) == null) {
					current.getContent().put(current.getName() + "/" + directoryFound, new ElfFile(current, directoryFound));
				}
			}else if (StringUtils.isNumeric(row.split(" ")[0])) {
				long fileSize = Long.parseLong( row.split(" ")[0] );
				String fileName = row.split(" ")[1];
				if(current.getContent().get(current.getName() + "/" + fileName) == null) {
					current.getContent().put(current.getName() + "/" + fileName, new ElfFile(current.getParent(), fileSize));
				}
			}
		}
		return root;
	}

	private Long getSize(HashMap<String, ElfFile> elfFiles, Long size) {
		for(ElfFile e : elfFiles.values())
		{
			if(!e.directory) {
				size = size + e.fileSize;
			}
			else {
				size = size + getSize(e.content, 0L);
			}
		}
		return size;
	}

	class ElfFile {
		ElfFile parent;
		String name;
		HashMap<String, ElfFile> content;
		boolean directory;
		long fileSize;
		
		public HashMap<String, ElfFile> getContent() {
			return content;
		}

		public void setContent(HashMap<String, ElfFile> contentHashMap) {
			this.content = contentHashMap;
		}

		public boolean isDirectory() {
			return directory;
		}

		public void setDirectory(boolean directory) {
			this.directory = directory;
		}

		public long getFileSize() {
			return fileSize;
		}

		public void setFileSize(long fileSize) {
			this.fileSize = fileSize;
		}
		
		public ElfFile getParent() {
			return parent;
		}

		public void setParent(ElfFile parent) {
			this.parent = parent;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ElfFile(ElfFile parentElfFile, String name) {
			this.setParent(parentElfFile);
			if(parentElfFile != null) {
				this.name = parentElfFile.getName() + "/" + name;
			} else {
				this.name = name;
			}
			this.setDirectory(true);
			content = new HashMap<>();
		}
		
		public ElfFile(ElfFile parentElfFile, long size) {
			this.setParent(parentElfFile);
			this.setDirectory(false);
			this.setFileSize(size);
		}
	}
}
