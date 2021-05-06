package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_21610 {
	static int N, M;
	static int[][] map, move;
	static int[] di = { 0, 0, -1, -1, -1, 0, 1, 1, 1 }, dj = { 0, -1, -1, 0, 1, 1, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		move = new int[M][2]; // [][0] : 방향, [][1] : 거리

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 2; j++) {
				move[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// ---------------- 입력 ----------------------

		// 초기 구름
		Queue<int[]> q = new LinkedList<int[]>();
		q.add(new int[] { N - 1, 0 });
		q.add(new int[] { N - 1, 1 });
		q.add(new int[] { N - 2, 0 });
		q.add(new int[] { N - 2, 1 });
		Queue<int[]> rain = new LinkedList<int[]>();
		
		for (int m = 0; m < M; m++) {
			boolean[][] visit = new boolean[N][N]; // 비 내리고 구름 사라진 곳 표시
			int d = move[m][0];
			int s = move[m][1];

			while (!q.isEmpty()) { // 비내리기
				int[] cloud = q.poll();
				// 구름 이동
				int ni = cloud[0] + di[d] * (s % N);
				int nj = cloud[1] + dj[d] * (s % N);

				if (ni < 0) ni = N + ni;
				else if (ni >= N) ni = ni - N;
				if (nj < 0) nj = N + nj;
				else if (nj >= N) nj = nj - N;

				map[ni][nj]++; // 비내리기
				visit[ni][nj] = true; // 내린 곳 표시
				rain.add(new int[] {ni, nj}); // 내린 곳 저장
			}
			// 물복사버그
			while(!rain.isEmpty()) {
				int[] now = rain.poll();
				
				for (int k = 2; k <= 8; k += 2) { // 대각선 탐색
					int ni = now[0] + di[k];
					int nj = now[1] + dj[k];

					if (ni >= 0 && ni < N && nj >= 0 && nj < N && map[ni][nj] > 0) {
						// 범위 안에 있고 바구니에 물이 차있으면 
						map[now[0]][now[1]]++;
					}
				}
			}
			// 구름 생성
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] >= 2 && !visit[i][j]) { // 바구니 물의 양 2 이상, 직전에 구름이 사라진 곳 x
						q.add(new int[] { i, j });
						map[i][j] -= 2; // 물 감소
					}
				}
			}
		}
		int answer = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				answer += map[i][j];
			}
		}
		System.out.println(answer);
	}
}
