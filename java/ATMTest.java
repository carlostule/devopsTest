import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class ATMTest {
  private static final DecimalFormat df = new DecimalFormat("0.00");
  public static void main(String[] args) {
    String userName = "";
    String password = "";
    String greeting = "";
    String withdrawString = "";
    int loginChances = 0;
    double cash = 1000.00;
    boolean login = false;
    List<WithdrawHistory> history = new ArrayList<WithdrawHistory>();
    Calendar calendar = new GregorianCalendar();


    Scanner name = new Scanner(System.in);
    Scanner pass = new Scanner(System.in);
    Scanner choice = new Scanner(System.in);
    Scanner wd = new Scanner(System.in);

    while (loginChances < 3 && !login) {
      System.out.println("Introduce tu nombre: ");
      userName = name.nextLine();
      System.out.println("Introduce password: ");
      password = pass.nextLine();

      if (passIsValid(password)) {
        if (calendar.get(Calendar.HOUR_OF_DAY) < 12) greeting = "Buenos días ";
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 12 && calendar.get(Calendar.HOUR_OF_DAY) < 19) greeting = "Buenas tardes ";
        else greeting = "Buenas noches ";
        login = true;
      } else {
        System.out.println("Password incorrecto!");

        if (loginChances == 1) {
          System.out.print("Te queda 1 intento.\n");
        } else {
          System.out.print("Te quedan " + (3 - (loginChances + 1)) + " intentos.\n");
        }
        
        loginChances++;
      }
    }

    while(login) {
      clearScreen();   
      System.out.println("-------------------------------------------------------------------");
      System.out.println("| "+ greeting + userName + " bienvenido a cajeros automaticos ACME     |");
      System.out.println("-------------------------------------------------------------------");
      System.out.println("| 1. Consultar saldo.                                             |");
      System.out.println("| 2. Retirar saldo.                                               |");
      System.out.println("| 3. Historico de movimientos.                                    |");
      System.out.println("| 4. Salir.                                                       |");
      System.out.println("-------------------------------------------------------------------");

      System.out.println("Digite una de nuestras opciones: ");
      int option = choice.nextInt();
      switch (option) {
        case 1:
          boolean subOpt = true;
          clearScreen();
          System.out.println("Tu saldo actual es: $" + df.format(cash) + " pesos.");
          while (subOpt) {
            submenu();
            int subOption = choice.nextInt();
            switch (subOption) {
              case 1:
                subOpt = false;
                break;
              case 2:
                System.out.println("Saliste del cajero.");
                subOpt = false;
                login = false;
                name.close();
                pass.close();
                choice.close();
                wd.close();
                break;
              default:
                System.out.println("No existe esa opcion en nuestro menu.");
                break;
            }
          }
          break;
        case 2:
          boolean subOpt2 =true;
          System.out.println("Cuanto deseas retirar: ");
          withdrawString = wd.nextLine();
          if (withdrawIsValid(withdrawString, cash)) {
            WithdrawHistory wdh = new WithdrawHistory(LocalDate.now().toString(), LocalTime.now().toString(), df.format(Double.parseDouble(withdrawString)), String.valueOf(cash));
            history.add(wdh);
            cash = cash - Double.parseDouble(withdrawString);
            System.out.println("Se retiro $" + df.format(Double.parseDouble(withdrawString)) + " pesos de efectivo.");
          } else if (Double.parseDouble(withdrawString) > cash) {
            System.out.println("No hay fondos suficientes para retirar.");
          } else {
            System.out.println("Cantidad erronea, debe introducir 2 decimales, no puede introducir cantidades negativas, letras o caracteres especiales.");
          }
          while (subOpt2) {
            submenu();
            int subOption = choice.nextInt();
            switch (subOption) {
              case 1:
                subOpt2 = false;
                break;
              case 2:
                System.out.println("Saliste del cajero.");
                subOpt2 = false;
                login = false;
                name.close();
                pass.close();
                choice.close();
                wd.close();
                break;
              default:
                System.out.println("No existe esa opcion en nuestro menu.");
                break;
            }
          }
          break;
        case 3:
          boolean subOpt3 = true;
          if (history.size() == 0) System.out.println("No hay historial de movimientos.");
          else {
            for (WithdrawHistory log : history) {
              System.out.println("--------------------");
              System.out.println("Fecha de retiro: " + log.getDate());
              System.out.println("Hora de retiro: " + log.getTime());
              System.out.println("Monto de retiro: " + log.getWithdraw());
              System.out.println("Monto anterior al retiro: " + df.format(log.getPreviousAmount()));
              System.out.println("--------------------");
            }
          }
          while (subOpt3) {
            submenu();
            int subOption = choice.nextInt();
            switch (subOption) {
              case 1:
                subOpt3 = false;
                break;
              case 2:
                System.out.println("Saliste del cajero.");
                subOpt3 = false;
                login = false;
                name.close();
                pass.close();
                choice.close();
                wd.close();
                break;
              default:
                System.out.println("No existe esa opcion en nuestro menu.");
                break;
            }
          }
          break;
        case 4:
          System.out.println("Saliste del cajero.");
          name.close();
          pass.close();
          choice.close();
          wd.close();
          login = false;
          break;
        default:
          System.out.println("No existe esa opcion en nuestro menu.");
          break;
      }
    }

    System.out.println("Hasta luego.");
  }

  public static boolean passIsValid(String input) {
    Pattern pattern = Pattern.compile("^[0-9]*$");
    return pattern.matcher(input).matches() && input.length() == 4 && Integer.parseInt(input) == 1235;
  }

  public static boolean withdrawIsValid(String input, Double currentCash) {
    Pattern regex = Pattern.compile("^[0-9]*.[0-9]{2}$");
    return regex.matcher(input).matches() && Double.parseDouble(input) <= currentCash && Double.parseDouble(input) >= 0.00;
  }

  public static void submenu() {
      System.out.println("-------------------------------------------------------------------");
      System.out.println("| Que quieres hacer?                                         |");
      System.out.println("-------------------------------------------------------------------");
      System.out.println("| 1. Regresar al menu.                                            |");
      System.out.println("| 2. Salir del cajero.                                            |");
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Digite una opcion: ");
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");  
    System.out.flush(); 
  }
}

class WithdrawHistory {
  String date = "";
  String time = "";
  String withdraw = "";
  String previousAmount = "";

  public WithdrawHistory(String date, String time, String withdraw, String previousAmount) {
    this.date = date;
    this.time = time;
    this.withdraw = withdraw;
    this.previousAmount = previousAmount;
  }

  public String getDate() {
    return this.date;
  }

  public String getTime() {
    return this.time;
  }

  public String getWithdraw() {
    return this.withdraw;
  }

  public Double getPreviousAmount() {
    return Double.parseDouble(this.previousAmount);
  }
}
