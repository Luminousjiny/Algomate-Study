import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_21608_상어초등학교 { // 2021 상반기 삼성 기출(오전)
	static StringTokenizer st;
	static int N, ANS;
	static int[][] map, pri;
	static int[] di = {-1,1,0,0};
	static int[] dj = {0,0,-1,1}; // 상하좌우
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		pri = new int[N*N][5];
		
		for (int i = 0; i < N*N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				pri[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i = 0; i < N*N; i++) {
			int[] like = new int[5];
			for (int j = 0; j < 5; j++) {
				like[j] = pri[i][j]; 
			}
			int person = like[0]; // 기록할 번호
			
			boolean able = false;
			boolean able2 = false;
			
			loop:
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++) {
					for (int m = 1; m < 5; m++) {
						if(map[j][k] == like[m]) {
							able = true;
							break loop;
						}
					}
				}
			}
			
			if(able) { // map에 친구 있을 때
				// 좋아하는 친구들 사방 탐색 후 0인칸이 있으면 수행
				loop:
				for (int j = 0; j < N; j++) {
					for (int k = 0; k < N; k++) {
						for (int m = 1; m < 5; m++) {
							if(map[j][k] == 0) continue; 
							else if(map[j][k] == like[m]) {
								for (int dir = 0; dir < 4; dir++) {
									int nx = j + di[dir];
									int ny = k + dj[dir];
									if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
									if(map[nx][ny] == 0) {
										able2 = true;
										break loop;
									}
								}
							}
						}
					}
				}
				if(able2) {
					ArrayList<Point> likeArr = new ArrayList<>();
					int[][] likeMap = new int[N][N];
					// 좋아하는 친구 많은 칸 찾기
					for (int j = 0; j < N; j++) {
						for (int k = 0; k < N; k++) {
							if(map[j][k] == 0) {
								for (int dir = 0; dir < 4; dir++) {
									int nx = j + di[dir];
									int ny = k + dj[dir];
									if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
									for (int l = 1; l < 5; l++) {
										if(map[nx][ny] == like[l]) likeMap[j][k]++;
									}
								}
								likeArr.add(new Point(j, k, likeMap[j][k]));
							}
						}
					}
					
					Collections.sort(likeArr);
					
					ArrayList<Pointx> Larr = new ArrayList<>();
					int likeMax = likeArr.get(0).pick;
					for (int j = 0; j < likeArr.size(); j++) {
						if(likeMax != likeArr.get(j).pick) break;
						Larr.add(new Pointx(likeArr.get(j).x, likeArr.get(j).y));
					}
					
					// 하나면 기록 , 많다면 그 칸 중 주변에 빈칸이 많은 칸 찾기
					if(Larr.size() == 1) {
						map[Larr.get(0).x][Larr.get(0).y] = person; // 기록
						continue; // 다음학생
					}else if(Larr.size() > 1) {
						ArrayList<Point> zeroArr = new ArrayList<>();
						int[][] zeroMap = new int[N][N];
						for (int j = 0; j < N; j++) {
							for (int k = 0; k < N; k++) {
								for (int m = 0; m < Larr.size(); m++) {
									if(j == Larr.get(m).x && k == Larr.get(m).y) { // 그 칸중에서 
										for (int dir = 0; dir < 4; dir++) {
											int nx = j + di[dir];
											int ny = k + dj[dir];
											if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
											if(map[nx][ny] == 0) zeroMap[j][k]++;
										}
										zeroArr.add(new Point(j, k, zeroMap[j][k]));
									}
								}
							}
						}
						
						Collections.sort(zeroArr);
						
						ArrayList<Pointx> Zarr = new ArrayList<>();
						int zeroMax = zeroArr.get(0).pick;
						for (int j = 0; j < zeroArr.size(); j++) {
							if(zeroMax != zeroArr.get(j).pick) break;
							Zarr.add(new Pointx(zeroArr.get(j).x, zeroArr.get(j).y));
						}
						
						// 하나면 기록, 많다면 x좌표 작은 칸
						if(Zarr.size() == 1) {
							map[Zarr.get(0).x][Zarr.get(0).y] = person; // 기록
							continue; // 다음학생
						}else if(Zarr.size() > 1) {
							Collections.sort(Zarr);
							
							ArrayList<Pointx> Xarr = new ArrayList<>();
							int minX = Zarr.get(0).x;
							for (int j = 0; j < Zarr.size(); j++) {
								if(minX != Zarr.get(j).x) break;
								Xarr.add(new Pointx(Zarr.get(j).x, Zarr.get(j).y));
							}
							
							// 하나면 기록, 많다면  y좌료 작은 칸
							if(Xarr.size() == 1) {
								map[Xarr.get(0).x][Xarr.get(0).y] = person; // 기록
								continue; // 다음학생
							}else if(Xarr.size() > 1) {
								int minY = Integer.MAX_VALUE;
								for (int j = 0; j < Xarr.size(); j++) {
									minY = Math.min(minY, Xarr.get(j).y);
								}
								for (int j = 0; j < Xarr.size(); j++) {
									if(Xarr.get(j).y == minY) {
										map[Xarr.get(j).x][Xarr.get(j).y] = person;
										break;
									}
								}
							}
						}
						
					}
				}
			}
			
			if(!able || !able2) { // map에 친구가 없거나, 있는데 인접한 자리가 빈칸이 아니면 
				// 빈칸 중 빈칸이 많은 칸 찾기
				ArrayList<Point> zeroArr = new ArrayList<>();
				int[][] zeroMap = new int[N][N];
				for (int j = 0; j < N; j++) {
					for (int k = 0; k < N; k++) {
						if(map[j][k] == 0) { // 빈칸
							for (int dir = 0; dir < 4; dir++) {
								int nx = j + di[dir];
								int ny = k + dj[dir];
								if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
								if(map[nx][ny] == 0) zeroMap[j][k]++;
							}
							zeroArr.add(new Point(j, k, zeroMap[j][k]));
						}
					}
				}
				
				Collections.sort(zeroArr);
				
				ArrayList<Pointx> Zarr = new ArrayList<>();
				int zeroMax = zeroArr.get(0).pick;
				for (int j = 0; j < zeroArr.size(); j++) {
					if(zeroMax != zeroArr.get(j).pick) break;
					Zarr.add(new Pointx(zeroArr.get(j).x, zeroArr.get(j).y));
				}
				
				// 하나면 기록, 많다면 x좌표 작은 칸
				if(Zarr.size() == 1) {
					map[Zarr.get(0).x][Zarr.get(0).y] = person; // 기록
					continue; // 다음학생
				}else if(Zarr.size() > 1) {
					Collections.sort(Zarr);
					
					ArrayList<Pointx> Xarr = new ArrayList<>();
					int minX = Zarr.get(0).x;
					for (int j = 0; j < Zarr.size(); j++) {
						if(minX != Zarr.get(j).x) break;
						Xarr.add(new Pointx(Zarr.get(j).x, Zarr.get(j).y));
					}
					
					// 하나면 기록, 많다면  y좌료 작은 칸
					if(Xarr.size() == 1) {
						map[Xarr.get(0).x][Xarr.get(0).y] = person; // 기록
						continue; // 다음학생
					}else if(Xarr.size() > 1) {
						int minY = Integer.MAX_VALUE;
						for (int j = 0; j < Xarr.size(); j++) {
							minY = Math.min(minY, Xarr.get(j).y);
						}
						for (int j = 0; j < Xarr.size(); j++) {
							if(Xarr.get(j).y == minY) {
								map[Xarr.get(j).x][Xarr.get(j).y] = person;
								break;
							}
						}
					}
				}
				
			}
			
		}
		int sum = 0;
		for (int j = 0; j < N; j++) {
			for (int k = 0; k < N; k++) {
				int now = map[j][k]; 
				int[] nowLike = new int[5];
				for (int i = 0; i < N*N; i++) {
					if(pri[i][0] == now) {
						for (int l = 0; l < 5; l++) {
							nowLike[l] = pri[i][l];
						}
					}
				}
				int cnt = 0;
				for (int dir = 0; dir < 4; dir++) {
					int nx = j + di[dir];
					int ny = k + dj[dir];
					if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
					for (int i = 1; i < 5; i++) {
						if(map[nx][ny] == nowLike[i]) cnt++;
					}
				}
				
				if(cnt != 0 ) {
					sum += (int) (Math.pow(10, cnt-1));
				}
			}
		}
		ANS = sum;
		System.out.println(ANS);
	}
	
	static class Point implements Comparable<Point>{
		int x, y, pick;
		Point(int x, int y, int pick){
			this.x = x;
			this.y = y;
			this.pick = pick;
		}
		@Override
		public int compareTo(Point o) {
			return Integer.compare(o.pick, this.pick); // 내림차순 
		}
	}

	static class Pointx implements Comparable<Pointx>{
		int x, y;
		Pointx(int x, int y){
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(Pointx o) {
			return Integer.compare(this.x, o.x); // 오름차순 
		}
	}
}
