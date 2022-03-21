import java.util.ArrayList;
import java.util.Arrays;

public class WorkingDays {

    // calculam numarul de zile in functie de secunde
    public static Long zileCalculator(Long secunde){
        return (secunde / 86400);
    }

    public static int[] lunaCurenta(Long zile) {
        int anBisect = 1; // 1 pentru an bisect; 2, 3, 4 pentru ani nebisecti
        int contorLuniPerAn = 1;
        int numarLuni = 1; // numarul de luni returnate in functie de secunde
        Integer luniScurte[] = {2, 4, 6, 9, 11}; // luni cu 30 sau 28 zile

        Integer luniLungi[] = {1, 3, 5, 7, 8, 10, 12}; // luni cu 31 zile

        int[] deReturnat = new int[3]; // numar luni, zile ramase si anul bisect
        while(zile >= 28) {
            if(anBisect == 1) { // in cazul anului bisect
                if (Arrays.asList(luniLungi).contains(contorLuniPerAn) && zile >= 31){
                    zile -= 31;
                    numarLuni += 1;
                } else if (Arrays.asList(luniScurte).contains(contorLuniPerAn) && contorLuniPerAn == 2 && zile >= 29){
                    // daca luna din iteratia curenta este scurta si este februarie scadem 29 zile
                    zile -= 29;
                    numarLuni += 1;
                } else if (Arrays.asList(luniScurte).contains(contorLuniPerAn) && contorLuniPerAn != 2 && zile >= 30){
                    zile -= 30;
                    numarLuni += 1;
                }
                contorLuniPerAn += 1;
                // schimbam luna
                if(contorLuniPerAn == 13){
                    // in caz ca trecem de decembrie, ne intoarcem la luna 0, adica ianuarie
                    contorLuniPerAn = 1;
                    // a trecut un an, schimba anul bisect
                    anBisect += 1;
                    if (anBisect == 5) {
                        // dupa ce au trecut 3 ani reinitializam anul bisect
                        anBisect = 1;
                    }
                }
            }
            if(anBisect != 1){
                // acceasi logica doar ca pentru un an nebisect, 28 zile scazute
                if (Arrays.asList(luniLungi).contains(contorLuniPerAn) && zile >= 31){
                    zile -= 31;
                    numarLuni += 1;
                } else if (Arrays.asList(luniScurte).contains(contorLuniPerAn) && contorLuniPerAn == 2 && zile >= 28){
                    zile -= 28;
                    numarLuni += 1;
                } else if (Arrays.asList(luniScurte).contains(contorLuniPerAn) && contorLuniPerAn != 2 && zile >= 30){
                    zile -= 30;
                    numarLuni += 1;
                }
                contorLuniPerAn += 1;
                if(contorLuniPerAn == 13){
                    contorLuniPerAn = 1;
                    anBisect += 1;
                    if (anBisect == 5) {
                        anBisect = 1;
                    }
                }
            }
        }
        deReturnat[0] = numarLuni;
        deReturnat[1] = Math.toIntExact(zile);
        deReturnat[2] = anBisect;
        return deReturnat;
    }

    public static void main(String[] args) {
        Long secunde = 1325462400L;
        Long  zile = zileCalculator(secunde); // calculez numarul de zile
        int[] ans = lunaCurenta(zile); // preiau datele prelucrate in functie de numarul de zile
        // numarLuni / zileRamase / anBisect
        int zileScurse = (int) (zile - ans[1]); // calculez cate zile s-au scurs pana la inceputul lunii curente
        int ziuaSaptamanaCurenta = zileScurse % 7 + 1; // calculez ce zi este prima din luna in functie de zilele scurse
        int luna = ans[0] % 12;
        int anBisect = ans[2];
        // pentru 4000 zile luna curenta este 0 (ianuarie) si ziua este 3 (joi)
        Integer luniScurte[] = {1, 3, 5, 8, 10};

        Integer luniLungi[] = {0, 2, 4, 6, 7, 9, 11};
        int zileLucratore = 1;

        if (anBisect == 1) { //verificam daca este an bisect
            if (Arrays.asList(luniScurte).contains(luna) && luna == 1) { //verificam daca este februarie(luna 1, indexare de la 0)
                int startZi = ziuaSaptamanaCurenta; // plecam din ziua calculata in functie de numarul de zile scurse
                int numarZileLuna = 29; // in functie de an bisect si luna luna / scurta initializam numarul de zile
                while (numarZileLuna != 0) {
                    if (startZi <= 4 && startZi >= 0) {
                        zileLucratore += 1; // contorizam numarul de zile lucratoare luni-vineri
                        startZi += 1;
                    } else startZi += 1;
                    if(startZi == 7) { // in caz ca am ajuns din nou la luni resetam
                        startZi = 0;
                    }
                    numarZileLuna -= 1; // micsoram numarul de zile ale lunii
                }
            }
            // acceasi logica dar pentru luna scurta dar nu februarie
            if (Arrays.asList(luniScurte).contains(luna) && luna != 1) {
                int startZi = ziuaSaptamanaCurenta;
                int numarZileLuna = 30;
                while (numarZileLuna != 0) {
                    if (startZi <= 4 && startZi >= 0) {
                        zileLucratore += 1;
                        startZi += 1;
                    } else startZi += 1;
                    if(startZi == 7) {
                        startZi = 0;
                    }
                    numarZileLuna -= 1;
                }
            }
            // acceasi logica pentru luni lungi
            if (Arrays.asList(luniLungi).contains(luna)) {
                int startZi = ziuaSaptamanaCurenta;
                int numarZileLuna = 31;
                while (numarZileLuna != 0) {
                    if (startZi <= 4 && startZi >= 0) {
                        zileLucratore += 1;
                        startZi += 1;
                    } else startZi += 1;
                    if(startZi == 7) {
                        startZi = 0;
                    }
                    numarZileLuna -= 1;
                }
            }
        } else {
            //repetam logica anterioara dar pentru ani nebisecti
            if (Arrays.asList(luniScurte).contains(luna) && luna == 1) {
                int startZi = ziuaSaptamanaCurenta;
                int numarZileLuna = 29;
                while (numarZileLuna != 0) {
                    if (startZi <= 4 && startZi >= 0) {
                        zileLucratore += 1;
                        startZi += 1;
                    } else startZi += 1;
                    if(startZi == 7) {
                        startZi = 0;
                    }
                    numarZileLuna -= 1;
                }
            }
            if (Arrays.asList(luniScurte).contains(luna) && luna != 1) {
                int startZi = ziuaSaptamanaCurenta;
                int numarZileLuna = 30;
                while (numarZileLuna != 0) {
                    if (startZi <= 4 && startZi >= 0) {
                        zileLucratore += 1;
                        startZi += 1;
                    } else startZi += 1;
                    if(startZi == 7) {
                        startZi = 0;
                    }
                    numarZileLuna -= 1;
                }
            }
            if (Arrays.asList(luniLungi).contains(luna)) {
                int startZi = ziuaSaptamanaCurenta;
                int numarZileLuna = 31;
                while (numarZileLuna != 0) {
                    if (startZi <= 4 && startZi >= 0) {
                        zileLucratore += 1;
                        startZi += 1;
                    } else startZi += 1;
                    if(startZi == 7) {
                        startZi = 0;
                    }
                    numarZileLuna -= 1;
                }
            }

        }

        System.out.println("Luna curenta este: ");
        System.out.println(luna);
        System.out.println("Incepe cu ziua de: ");
        if (ziuaSaptamanaCurenta == 0){
            System.out.println("luni");
        }
        if (ziuaSaptamanaCurenta == 1){
            System.out.println("marti");
        }
        if (ziuaSaptamanaCurenta == 2){
            System.out.println("miercuri");
        }
        if (ziuaSaptamanaCurenta == 3){
            System.out.println("joi");
        }
        if (ziuaSaptamanaCurenta == 4){
            System.out.println("vineri");
        }
        if (ziuaSaptamanaCurenta == 5){
            System.out.println("sambata");
        }
        if (ziuaSaptamanaCurenta == 6){
            System.out.println("duminica");
        }
        System.out.println("Si are urmatorul numar de zile lucratoare: ");
        System.out.println(zileLucratore);

    }
}