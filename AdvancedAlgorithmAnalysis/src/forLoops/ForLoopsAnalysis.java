package forLoops;

public class ForLoopsAnalysis {

	static int opp;
	
	public static void main(String[] args) {
		int n=11;
		
		//System.out.println( "      n = " +F1(n) );
		//System.out.println( "      n = " +F2(n) );
		//System.out.println( "      n = " +F3(n) );
		//System.out.println( "      n = " +F4(n) );
		System.out.println( "      n = " +F5(n) );
		System.out.println( "      n = " +F6(n) );

		
	}
	
	static int F1(int n){
		opp=0;
		for( int i=0; i<=n; i++) {
			opp++;
			System.out.println("i = "+i +"     opp = "+opp);
		}
		return n;
	}
	
	static int F2(int n){
		opp=0;
		for( int i=1; i<=n; i++) {
			opp++;
			System.out.println("i = "+i +"     opp = "+opp);
		}
		return n;
	}
	
	static int F3(int n){
		opp=0;
		for( int i=0; i<=n; i+=2) {
			opp++;
			System.out.println("i = "+i +"     opp = "+opp);
		}
		return n;
	}
	
	static int F4(int n){
		opp=0;
		for( int i=0; i<=n*n; i++) {
			opp++;
			System.out.println("i = "+i +"     opp = "+opp);
		}
		return n;
	}
	
	static int F5(int n){
		opp=0;
		for( int i=0; i<=n; i+=2) {
			opp++;
			System.out.println("i = "+i +"     opp = "+opp);
		}
		return n;
	}
	
	static int F6(int n){
		opp=0;
		for( int i=0; i<=n; i++) {
			for( int j=i; j<=n; j++) {
				opp++;
				System.out.println("j = "+j + "   i = "+i +"     opp = "+opp);
			}
		}
		return n;
	}
}
