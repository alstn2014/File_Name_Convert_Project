package common;

public class FileManager {
	// ��ü Ǯ ��ο��� ���ϸ� �����ϱ�
	public static String getPrefix(String filename) {
		String[] result=filename.split("\\.");

		return result[0];
	}
	
	//���ϸ��� �ѱ�� Ȯ���ڸ� ��ȯ�Ѵ�
	public static String getExtend(String filename) {
		String[] result=filename.split("\\.");

		return result[1];
	}
	
	// ���丮 ��� ����
	public static String extractDir(String path) {
		int lastIndex = path.lastIndexOf("\\");

		return path.substring(0, lastIndex);
	}

	public static void main(String[] args) {
		
	}

}
