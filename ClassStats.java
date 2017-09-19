/* ClassStats by Kevin Glenn
Kevin Glenn
Description: class stats program
*/

import java.util.*;
import java.io.*;

public class ClassStats
{
	public static void main(String[] args)
	{
		System.out.println("ClassStats by Kevin Glenn");
		System.out.println();
		String input = null;
		int numLines = 0;
		String firstLine = null;
		String secondLine = null;
		boolean studentFound = false;
		boolean assignmentFound = false;
		ArrayList<String> inputFile = new ArrayList<>();

		try 
		{
			File file = new File(args[0]);
			Scanner fileScan = new Scanner(file);

			while (fileScan.hasNext())
			{
				inputFile.add(fileScan.nextLine());
				numLines++;
			}
		}

		catch (FileNotFoundException ex)
		{
			System.out.println("Please enter a valid file name.");
			System.exit(0);
		}

		String loadedFile = args[0];
		System.out.println("Loaded file " + loadedFile);


		for (;;)
		{
			Scanner scan = new Scanner(System.in);
			System.out.print("> ");
			input = scan.nextLine();
			String[] commands = input.split(" ");

			try 
			{
				if (commands[0].equalsIgnoreCase("help"))
					commands();


				if (commands[0].equalsIgnoreCase("exit"))
					System.exit(0);


				if (commands[0].equalsIgnoreCase("roll"))
				{
					secondLine = inputFile.get(0);
					String [] splitSecond = secondLine.split(",");
					System.out.println("    Student Grades for " + 
						splitSecond[0] + ", " + splitSecond[1]);

					secondLine = inputFile.get(1);
					splitSecond = secondLine.split(",");

					int totalPoints = 0;

					for (int i = 2; i < splitSecond.length; i++)
					{
						totalPoints += Integer.parseInt(splitSecond[i]);
					}

					System.out.println("    Total points possible: " + totalPoints);
					System.out.println();
					
					String firstNameHeader = "First Name";
					String lastNameHeader = "Last Name";
					String pointsHeader = "Points";
					String gradeHeader = "Grade";
					
					System.out.printf("    %-15s %-10s %8s %8s", firstNameHeader, lastNameHeader,  pointsHeader, gradeHeader);
					System.out.println();
					System.out.printf("    %-15s %-10s %8s %8s", "----------", "---------", "------", "-----");
					System.out.println();

					totalPoints = 0;

					for (int i = 2; i < numLines; i++)
					{
						String readLine = inputFile.get(i);
						String[] readNames = readLine.split(",");

						for (int j = 2; j < readNames.length; j++)
						{
							totalPoints += Double.parseDouble(readNames[j]);
						}

						String grades = getGrades(totalPoints);
						System.out.printf("    %-15s %-10s %7d %8s \n", readNames[0], readNames[1], totalPoints, grades);
						totalPoints = 0;
					}

				}


				if (commands[0].equalsIgnoreCase("assignments"))
				{
					secondLine = inputFile.get(1);
					firstLine = inputFile.get(0);
					String[] splitFirst = firstLine.split(",");
					String[] splitSecond = secondLine.split(",");
					System.out.println("    Assignments for " + splitFirst[0] + ", " + splitFirst[1]);
					System.out.println();

					String assignmentHeader = "Assignment";
					String pointsHeader = "Points";

					System.out.printf("    %-15s %8s", assignmentHeader, pointsHeader);
					System.out.println();
					System.out.printf("    %-15s %8s", "----------", "------");
					System.out.println();

					for (int i = 2; i < splitFirst.length; i++)
					{
						System.out.printf("    %-15s %8s\n", splitFirst[i], splitSecond[i]);
					}

				}


				if(commands[0].equalsIgnoreCase("search"))
				{
					String firstNameHeader = "First Name";
					String lastNameHeader = "Last Name;";
					String pointsHeader = "Points";
					String gradeHeader = "Grade";

					for (int i = 2; i < numLines; i ++)
					{
						for (int x = 0; x < 2; x ++)
						{
							String readLine = inputFile.get(i);
							String[] readNames = readLine.split(",");
							String grades = getGrades(getTotalPoints(readNames));

							if (readNames[x].toLowerCase().contains(commands[1].toLowerCase()) || readNames[x+1].toLowerCase().contains(commands[1].toLowerCase()))
							{
								System.out.printf("    %-15s %-10s %8s %8s\n", firstNameHeader, lastNameHeader,  pointsHeader, gradeHeader);
								System.out.printf("    %-15s %-10s %8s %8s\n", "----------", "---------", "------", "-----");
								System.out.printf("    %-15s %-10s %8s %8s\n", readNames[0], readNames[1], getTotalPoints(readNames), grades);
								break;
							}
						}
					}
				}


				try // this probably wasn't the best way to do this
				{
					if (commands[0].equalsIgnoreCase("student"))
					{
						String assignmentHeader = "Assignment";
						String pointsHeader = "Points";
						String possibleHeader = "Possible";

						for (int i = 2; i < numLines; i++)
						{
							for (int x = 0; x < 2; x++)
							{
								String readLine = inputFile.get(i);
								String[] readNames = readLine.split(",");

								if (readNames[x].toLowerCase().contains(commands[1].toLowerCase()) && readNames[x+1].toLowerCase().contains(commands[2].toLowerCase()))
								{
									System.out.println("    Grades for " + readNames[0] + " " + readNames[1]);
									System.out.println();
									String line1 = inputFile.get(0);
									String[] splitLine1 = line1.split(",");
									String line2 = inputFile.get(1);
									String[] splitLine2 = line2.split(",");
									String line3 = inputFile.get(i);
									String[] splitLine3 = line3.split(",");
									
									double total = getTotalPoints(readNames);
									String grade = getGrades(total);
									
									System.out.printf("    %-15s %8s %10s\n", assignmentHeader, pointsHeader, possibleHeader);
									System.out.printf("    %-15s %8s %10s\n", "----------", "------", "--------");

									for (int y = 2; y < splitLine1.length; y++)
									{
										System.out.printf("    %-15s %8s %10s\n", splitLine1[y], splitLine3[y], splitLine2[y]);
									}

									System.out.printf("    %-15s %8s %10s\n", "total", total, "100");
									System.out.println();
									System.out.println("Final Grade: " + grade);

									studentFound = true;
									break;
								}
							}
						}
						//handle student not found
						if (!studentFound) System.out.println("Student not found. Please try again.");
					}	
				}

				catch (ArrayIndexOutOfBoundsException ex)
				{
					System.out.println("Student not found. Please try again.");
				}


				if (commands[0].equalsIgnoreCase("assignment"))
				{
					int[] gradeCount = new int[numLines];
					double total = 0.0;

					outerloop:
					for (int i = 0; i < numLines; i++)
					{
						String line1 = inputFile.get(0);
						String[] splitLine1 = line1.split(",");
						String line2 = inputFile.get(1);
						String[] splitLine2 = line2.split(",");

						int[] grades = new int[numLines];

						for (int j = 0; j < splitLine1.length; j++)
						{
							try
							{
								if (splitLine1[j].toLowerCase().contains(commands[1].toLowerCase()) && splitLine1[j].toLowerCase().contains(commands[2].toLowerCase()))
								{
									System.out.println("    " + splitLine1[j] + ":   " + splitLine2[j] + " points");
									int totalPoints = Integer.parseInt(splitLine2[3]);

									for (int x = 2; x < numLines; x++)
									{
										String everyLine = inputFile.get(x);
										String[] splitAll = everyLine.split(",");
										total = Integer.parseInt(splitAll[j]);
										double gradePercent = (total / Integer.parseInt(splitLine2[j]) * 100);
										String currentGrade = getGrades(gradePercent);

										if (currentGrade.equals("A"))
											grades[0]++;
										if (currentGrade.equals("B"))
											grades[1]++;
										if (currentGrade.equals("C"))
											grades[2]++;
										if (currentGrade.equals("D"))
											grades[3]++;
										if (currentGrade.equals("F"))
											grades[4]++;
									}
									System.out.println();
									System.out.println("    Grade Breakdown");
									System.out.println("    A:  " + grades[0]);
									System.out.println("    B:  " + grades[1]);
									System.out.println("    C:  " + grades[2]);
									System.out.println("    D:  " + grades[3]);
									System.out.println("    F:  " + grades[4]);

									assignmentFound = true;
									break outerloop;
								}
							}

							catch (ArrayIndexOutOfBoundsException ex)
							{
								System.out.println("    " + splitLine1[j] + ":   " + splitLine2[j] + " points");
								int totalPoints = Integer.parseInt(splitLine2[3]);

								for (int x = 2; x < numLines; x++)
								{
									String everyLine = inputFile.get(x);
									String[] splitAll = everyLine.split(",");
									total = Integer.parseInt(splitAll[j]);
									double gradePercent = (total / Integer.parseInt(splitLine2[j]) * 100);
									String currentGrade = getGrades(gradePercent);
									
									if (currentGrade.equals("A"))
										grades[0]++;
									if (currentGrade.equals("B"))
										grades[1]++;
									if (currentGrade.equals("C"))
										grades[2]++;
									if (currentGrade.equals("D"))
										grades[3]++;
									if (currentGrade.equals("F"))
										grades[4]++;
								}

								System.out.println();
								System.out.println("    Grade Breakdown");
								System.out.println("    A:  " + grades[0]);
								System.out.println("    B:  " + grades[1]);
								System.out.println("    C:  " + grades[2]);
								System.out.println("    D:  " + grades[3]);
								System.out.println("    F:  " + grades[4]);

								assignmentFound = true;
								break outerloop;
							}
						}
					}

					if(!assignmentFound) System.out.println("Assignment not found. Please try again.");

				}


				if (commands[0].equalsIgnoreCase("report"))
				{
					secondLine = inputFile.get(0);
					String[] splitSecond = secondLine.split(",");
					System.out.println("    Grade breakdown for " + splitSecond[0] + ", " + splitSecond[1]);
					System.out.println();
					
					int[] gradePoints = new int[5];
					int[] totalPointsArray = new int[numLines];
					
					int totalPoints = 0;
					int temp = totalPoints; 

					for (int i = 2; i < numLines; i ++)
					{
						String readLine = inputFile.get(i);
						String[] readNames = readLine.split(",");

						for (int j = 2; j < readNames.length; j++)
						{
							totalPoints += Double.parseDouble(readNames[j]);
						}

						String currentGrade = getGrades(totalPoints);
						int maxValue = getMaxValue(totalPointsArray);
						int minValue = getMinValue(totalPointsArray);

						if (currentGrade.equals("A"))
							gradePoints[0]++;
						else if (currentGrade.equals("B"))
							gradePoints[1]++;
						else if (currentGrade.equals("C"))
							gradePoints[2]++;
						else if (currentGrade.equals("D"))
							gradePoints[3]++;
						else if (currentGrade.equals("F"))
							gradePoints[4]++;

						temp = totalPoints;
						totalPoints = 0;
						totalPointsArray[i] = temp;
					}

					int maxValue = getMaxValue(totalPointsArray);
					int minValue = getMinValue(totalPointsArray);
					int aveValue = getAveValue(totalPointsArray);
							
					System.out.println("    A:  " + gradePoints[0]);
					System.out.println("    B:  " + gradePoints[1]);
					System.out.println("    C:  " + gradePoints[2]);
					System.out.println("    D:  " + gradePoints[3]);
					System.out.println("    F:  " + gradePoints[4]);
					System.out.println();
					System.out.println("    Low: " + minValue + "%");
					System.out.println("    High: " + maxValue + "%");
					System.out.println("    Average: " + aveValue + "%");

				}

			}

			catch (InputMismatchException ex) //this didn't quite work how i wanted it to
			{
				commands();
			}

			if (!commands[0].equalsIgnoreCase("exit") && !commands[0].equalsIgnoreCase("roll") && !commands[0].equalsIgnoreCase("help") && !commands[0].equalsIgnoreCase("search") &&
				!commands[0].equalsIgnoreCase("assignments") && !commands[0].equalsIgnoreCase("report") && !commands[0].equalsIgnoreCase("student") && !commands[0].equalsIgnoreCase("assignment"))
			System.out.println("Please enter a valid command. Type 'help' for more options.");
		}

	}

	public static void commands()
	{
		String s = String.join("\n" 
			, "    Accepted commands:"
			, "    exit"
			, "    help"
			, "    roll"
			, "    search [partial name]"
			, "    assignments"
			, "    report"
			, "    student [student name]"
			, "    assignment [assignment name]"
		);
		System.out.println(s);
	}
	public static String getGrades(double points)
	{
		String letterGrade = null;
		if (points >= 90)
			letterGrade = "A";
		else if (points >= 80 && points < 90)
			letterGrade = "B";
		else if (points >= 70 && points < 80)
			letterGrade = "C";
		else if (points >= 60 && points < 70)
			letterGrade = "D";
		else if (points >= 60)
			letterGrade = "F";
		else 
			letterGrade = ""; //guess this'll have to do
		return letterGrade;
	}

	public static int getMaxValue(int[] points)
	{
		int max = points[0];
		for (int i = 2; i < points.length; i++)
		{
			if (points[i] > max)
				max = points[i];
		}
		return max;
	}

	public static int getMinValue(int[] points)
	{
		int min = points[2];
		for (int i = 2; i < points.length; i++)
		{
			if (points[i] < min)
				min = points[i];
		}
		return min;
	}

	public static int getAveValue(int[] points)
	{
		int sum = 0;
		for (int i = 2; i < points.length; i++)
		{
			sum += points[i];
		}
		int average = sum / (points.length - 2);
		return average;
	}

	public static double getTotalPoints(String[] readNames)
	{
		double sum = 0.0;
		
		for (int i = 2; i < readNames.length; i++)
		{
			sum += Double.parseDouble(readNames[i]);
		}
		return sum;
	}


}