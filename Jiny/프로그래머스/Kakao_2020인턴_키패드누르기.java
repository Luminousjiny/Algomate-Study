
public class Kakao_2020인턴_키패드누르기 {

	public static void main(String[] args) {
		int[] number = {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5};
		String ans = solution (number, "right");
		System.out.println(ans);
	}
	static Point now, left, right;
	public static String solution(int[] numbers, String hand) {
        String answer = "";
        
        int[][] pad = new int[4][3];
        int num = 1;
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				pad[i][j] = num;
				num++;
			}
		}
        pad[3][0] = pad[3][2] = -1;
        pad[3][1] = 0;
        
        left = new Point(3, 0);
        right = new Point(3, 2);
        
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < numbers.length; n++) {
        	loop:
        	 for (int i = 0; i < 4; i++) {
     			for (int j = 0; j < 3; j++) {
     				if(pad[i][j] == numbers[n]) {
     					now = new Point(i, j);
     					break loop;
     				}
     			}
     		}// 현재 갈 지점 파악
        
        	if(numbers[n] == 1 || numbers[n] == 4 || numbers[n] == 7) { // left
        		sb.append("L");
        		left = now;
        	}else if(numbers[n] == 3 || numbers[n] == 6 || numbers[n] == 9) { // right
        		sb.append("R");
        		right = now;
        	}else { // 둘 중 가까운 곳, 같으면 hand 
        		int left_len = Math.abs(left.x - now.x) + Math.abs(left.y - now.y);
        		int right_len = Math.abs(right.x - now.x) + Math.abs(right.y - now.y);
        		if(left_len > right_len) {
        			sb.append("R");
	        		right = now;
        		}else if(left_len < right_len){
        			sb.append("L");
	        		left = now;
        		}else {
        			if(hand.equals("left")) {
        				sb.append("L");
		        		left = now;
        			}else if(hand.equals("right")) {
        				sb.append("R");
		        		right = now;
        			}
        		}
        	}
        
        }
        answer = sb.toString();
        return answer;
    }
	
	static class Point{
		int x, y;
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

}
