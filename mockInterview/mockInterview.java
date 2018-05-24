/*The task is to write a program that simulates the interviewing process for an imaginary company called MARS. 
*There are 3 different open positions: accountant, academic and software engineering. Each of them have some common 
*and some specific requirements. The program should have at least two static methods in addition to the main method. 
*And at least two of those methods should be returning information back to its caller method.
*/






import java.util.Scanner;
public class interview{
	public static void main(String[] args) {
		Scanner console= new Scanner(System.in);
		department(intro(console),console);
	}

	// This method is used for questioning the  common requirements.
	public static int intro(Scanner console) {
		System.out.println("Welcome to MARS Corp. interviewing system! ");
		System.out.print("Can I learn your name? ");
		String name = console.nextLine(); // Name of the applicant.
		System.out.print("Hello, " + upper(name) + ". How old are you? ");
		int age = console.nextInt(); // Age of the applicant
		if(age < 18) {
			int a = 18 - age;
			System.out.println("Unfourtunately, we are looking for older employees.");
			System.out.print("I'm looking forward to seeing you in "+ a + " years.");
			return 0;
		}
		else {
			System.out.print("What is your gender? (Male/Female) ");
			String gender = console.next(); // Gender of the applicant
			if(check("male","female",gender,console).equalsIgnoreCase("Male")) { 
				System.out.print("Have you completed your military service? (Yes/No) ");
				String m = console.next(); // Applicant's answer to military service question.
				if (check("yes","no",m,console).equalsIgnoreCase("no")) {
					System.out.println("Sorry, you don't meet the requirments.");
					return 0;
				}
			}
			System.out.print("Do you have a college degree? (Yes / No) ");
			String d = console.next();  //Applicant's answer to college degree question.
			if(check("yes","no",d,console).equalsIgnoreCase("no")){
				System.out.println("Sorry,unless you're Bill Gates or Mark Zuckerberg; you don't meet the requirments.");
				return 0;
			}
			else {

				return 1;
			}
		}
	}
	// This method leads program to the specific job positions.
	public static void department(int a, Scanner console) {
		if(a==1) {
			System.out.print("Great! For which position are you applying for? ");
			console.nextLine();
			String position = console.nextLine();  //The position which applicant is applying for. 
			if ((position).equalsIgnoreCase("Software Engineering")){ 
				computer(console);
			}
			else if(position.equalsIgnoreCase("Academic")) {
				academic(console);
			}

			else if(position.equalsIgnoreCase("Accountant")) {
				accountant(console);
			}
			else {
				System.out.print("There's no available poisition in this field. Please subscribe to our mail list to be notified on updates.");
			}
		}
	}

	//This method asks questions to see if the applicant fulfill the requirements for academic
	public static void academic(Scanner console) {
		System.out.print("Please enter number of papers you've published: ");
		int paper=console.nextInt(); // Number of papers' applicant have published
		if(paper<3) {
			System.out.print("Sorry, to be eligible you must pusblish at least 3 papers. ");
		}
		else {
			System.out.print("Are you fluent in English? (Yes/No) ");
			String eng = console.next();  //Applicant's answer to english fluency question.
			if (check("yes","no",eng,console).equalsIgnoreCase("No")) {
				System.out.print("Please attend an \"English course\" before trying again.");
			}
			else {
				System.out.print("Are you passionate about teaching? (Yes / No) ");
				String x = console.next();  //Applicant's answer to passion of teaching question.

				if(check("yes","no",x,console).equalsIgnoreCase("yes")) {
					System.out.println("Congratulations! You got the job. ");
				}
				else {
					System.out.print("Sorry, if you don't like teaching this job will be a nightmare for you. ");
				}
			}
		}

	}

	//This method asks questions to see if the applicant fulfill the requirements for accountant
	public static void accountant(Scanner console) {
		System.out.print("In which field do you have a college degree? ");
		String field = console.next(); // Applicant's college major
		if(field.equalsIgnoreCase("accountant")) {
			System.out.print("Are you comfortable with using Excel? (Yes/No) ");
			String excel=console.next(); //  //Applicant's answer to Excel question.
			if(check("yes","no",excel,console).equalsIgnoreCase("No")) {
				System.out.print("This answer terminated the interview. ");
			}
			else {
				System.out.print("Do you have a driving license? (Yes/No) ");
				String licence= console.next(); // The answer of whether he has a driving licence or not.
				if(check("yes","no",licence,console).equalsIgnoreCase("no")) {
					System.out.print("Hoping to see you after your driving course. Goodbye! ");
				}
				else {
					System.out.print("How many of our current employees can give reference for you? ");
					int ref = console.nextInt(); //Applicant's number of reference
					if(ref<2) {
						System.out.print("Sorry, you can't get the job. A friendly advice: \"Network is important.\" ");
					}
					else {
						System.out.print("Are you fluent in English? (Yes/No) ");
						String eng=console.next(); //Applicant's answer for English fluency question
						if(check("yes","no",eng,console).equalsIgnoreCase("yes")) {
							System.out.print("You are more than welcome to our company. Congratulations! ");
						}
						else {
							System.out.print("Do you have a friend who can translate for you?(Yes/No) ");
							String tra=console.next(); // Applicant's answer to whether he has a friend who can translate.
							if(check("yes","no",tra,console).equalsIgnoreCase("yes")) {
								System.out.print("Congratulations, you got the job but you'd better split your salary with him/her.");
							}
							else {
								System.out.println("If that is the case, your journey is over :( ");
							}
						}
					}
				}
			}
		}
		else {
			System.out.print("Your major doesn't match the position you're applying for.");
		}
	}


	//This method asks questions to see if the applicant fulfill the requirements for software engineering

	public static void computer(Scanner console) {
		System.out.print("In which field do you have a college degree? ");
		String field = console.nextLine();// Applicant's college major
		if(field.equalsIgnoreCase("computer engineering")||field.equalsIgnoreCase("computer science")||field.equalsIgnoreCase("computer engineering")) {
			if(prLang(console)< -1) {
				System.out.print("This position requires knowledge of at least 2 programming languages ");
			}
			else {
				System.out.print("Do you have a graduate degree in software engineering? (Yes/No) ");
				String deg= console.next(); //  //Applicant's answer to whether he has a graduate degree.
				if(check("yes","no",deg,console).equalsIgnoreCase("Yes")) {
					System.out.print("Congratulations! You're now part of our big family.");
				}
				else{
					System.out.print("Have you worked as a software engineer before? (Yes/No) ");
					String exp= console.next(); // Applicant's answer to whether he has experience
					if(check("yes","no",exp,console).equalsIgnoreCase("yes")){
						System.out.print("How many years of experience do you have? ");
						int year = console.nextInt(); // Years of experience
						if(year>=3) {
							System.out.print("Congratulations! You're hired. ");
						}
						else {
							System.out.print("Sorry you're a bit naive. But hey, keep up the good work");
						}
					}
					else {
						System.out.print("Sorry you aren't hired.");
					}
				}
			}
		}
		else {
			System.out.print("Your major doesn't match the position you're applying for.");
		}
	}

	//This method is used to see if applicant knows at least two programming languages
	public static int prLang(Scanner console) {
		int p=0;
		System.out.print("Cool! Do you now how to program in JAVA? ");
		String p1= console.next().toLowerCase(); // Whether applicant knows how to code in Java
		p+=check("yes","no",p1,console).indexOf("y");
		System.out.print("Do you now how to program in Python? ");
		String p2= console.next().toLowerCase();// Whether applicant knows how to code in Python
		p+=check("yes","no",p2,console).indexOf("y");
		System.out.print("Do you now how to program in C++? ");
		String p3= console.next().toLowerCase(); // Whether applicant knows how to code in C++
		p+=check("yes","no",p3,console).indexOf("y");
		return p;
	}

	//This method is used to format the name 
	public static String upper(String name){ 
		int a = name.indexOf(" "); // index of blank space
		return name = name.substring(0,1).toUpperCase() + name.substring(1,a).toLowerCase();
	}

	//This method is for preventing wrong answers
	public static String check(String a,String b, String c,Scanner console) {
		while(!((c.equalsIgnoreCase(a)||(c.equalsIgnoreCase(b))))) {
			System.out.print("Please enter a valid input! ");
			c=console.next(); // new input from the user
		}
		return c;
	}

}



