
package apprsa;
import java.math.BigInteger;
import java.util.*;
public class AppRSA {
    //variables para el algoritmo RSA
    static int n;
    static int e;
    static int d;
    static int fi;
    
    //variables para la criba
    static int criba_tam;
    static boolean criba[];
    static List<Integer> primos=new ArrayList<Integer>();
    //may_term es el tamaño que tendrá la criba
    //valB,valF será el intervalo en el que estaran los primos en la lista
    static void criba(int may_term, int valB,int valF)              //1
    {                                                               
        int c=0;                                                    //1
        criba_tam=may_term;                                         //1
        criba=new boolean[criba_tam];                               //1
        Arrays.fill(criba,true);                               //1
        criba[0]=criba[1]=false;                                    //1
        for(long i=2;i<criba_tam;i++)                               //criba_tam-1
        {
            if(criba[(int)i])                                       //criba_tam-2
            {
                for(long j=i*i;j<criba_tam;j+=i)                    //criba_tam-2(raizde criba_tam)
                {
                    criba[(int)j]=false;                            //criba_tam-2(raizde criba_tam)
                }
                if(i>=valB && i<=valF)                              
                    primos.add((int)i);
            }
        }
    }
    //Determina el maximo comun divisor entre dos numeros
    static int mcm(int e,int fi)
    {
        int m=fi%e;
        int x=0;
        while (m!=0)
        {
            x=e;
            e=m;
            m=x%e;
        }
        return e;
    }
    
    
    
    static int Res_congr(int e, int fi)
    {
        int k=1;
        int m=(1+(k*fi))%e;
        while(m!=0)
        {
            k=k+1;
            m=(1+(k*fi))%e;
        }
        int d=(int)((1+(k*fi))/e);
        return d;
    }
    
    static String cifrar(String men, int n, int e)
    {
        int t,y,m,z;
        men=men.toLowerCase();
        String menci="";
        for(int i=0;i<men.length();i++)
        {
            if((men.charAt(i))!=(' '))
            {
                t=((int)men.charAt(i));
//                y=(int)Math.pow(t,e);
//                z=y%n;
//                System.out.println("z="+z+" char="+t+" y="+y+ " y%n="+y%n+" t^e=");
//                menci=menci+Long.toString(z)+" ";
                
                BigInteger in=new BigInteger(Integer.toString(t));
                BigInteger re=new BigInteger(Integer.toString(t));
                for(int r=2;r<=e;r++)
                {
                    in=in.multiply(re);
                }
                re=BigInteger.valueOf(n);
                menci=menci+in.mod(re)+" ";
                
                
            }
            else
            {
                menci=menci+"20 ";
            }
        }
        return menci;
    }
    static String Decifrar(String cifr, int n, int d)
    {
        int t;
        char c=61;
        String menOri="";
        String cif[]=cifr.split(" ");
        for(int i=0;i<cif.length;i++)
        {
            if(Integer.parseInt(cif[i])!=20)
            {
                BigInteger in=new BigInteger(cif[i]);
                BigInteger re=new BigInteger(cif[i]);
                for(int r=2;r<=d;r++)
                {
                    in=in.multiply(re);
                }
                re=BigInteger.valueOf(n);
                int x=(in.mod(re)).intValue();
                c=(char)x;
                menOri=menOri+c;
                
            }
            else
            {
                menOri=menOri+" ";
            }
        }
        return menOri;
    }
    
    
    public static void main(String[] args) {
        Scanner leer=new Scanner(System.in);
        
        int vB=10,vF=99;
        criba(1000,vB,vF);
        int ptam=primos.size();
        
        //tomamos dos valores primos aleatorios(p,q) dentro del rango vB-vF
        int i=(int)((Math.random())*ptam),p=primos.get(i);
        primos.remove(i);
        ptam=primos.size();
        int j=(int)((Math.random())*ptam),q=primos.get(j);
        primos.remove(j);
        ptam=primos.size();
        n=p*q;
        int fi=(p-1)*(q-1);
        int k=(int)((Math.random())*ptam),e=primos.get(k);
        primos.remove(k);
        ptam=primos.size();
        
        d=Res_congr(e,fi);
        int[] clavePu=new int[2];clavePu[0]=n;clavePu[1]=e;
        int[] clavePr=new int[2];clavePr[0]=n;clavePr[1]=d;
        
        System.out.println("Escriba su mensaje");
        String m=leer.nextLine();
        m=cifrar(m,n,e);
        System.out.println("");
        System.out.println("El mensaje cifrado es: ");
        System.out.println(m+"\n");
        System.out.println("Ahora el mensaje descifrado es");
        m=Decifrar(m,n,d);
        System.out.println(m+"\n");
        System.out.printf("Estos son los datos del algoritmo \np=%d, q=%d, n=%d,fi=%d,e=%d,d=%d \n",p,q,n,fi,e,d);
        
    }
    
}
