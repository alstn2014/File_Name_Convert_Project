package common;

public class FileManager {
	// 전체 풀 경로에서 파일명만 추출하기
	public static String getPrefix(String filename) {
		String[] result=filename.split("\\.");

		return result[0];
	}
	
	//파일명을 넘기면 확장자를 반환한다
	public static String getExtend(String filename) {
		String[] result=filename.split("\\.");

		return result[1];
	}
	
	// 디렉토리 경로 추출
	public static String extractDir(String path) {
		int lastIndex = path.lastIndexOf("\\");

		return path.substring(0, lastIndex);
	}

	public static void main(String[] args) {
		
	}

}
