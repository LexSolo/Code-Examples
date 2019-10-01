using System;
using System.IO;
using System.Text.RegularExpressions;

namespace C_Projects
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Welcome to the program of performing the first test task by Aleksandr Solovyov.");
            Console.WriteLine("The program able to manipulate file you choose.");
            Console.WriteLine("The file can be read, written, also some words less than definite length can be deleted such as a punctuation marks.");
            Console.WriteLine("After all manipulations the changes happened through the process save in the new file.");
            Console.WriteLine("So let's start!");
            Console.WriteLine("\nWould you like to delete ");
            
            bool flag = true;
            int simbolValue;
            int punctMark;
            bool mark = true;
            string[] text = new string[1];

            //input interface
            while (flag)
            {
                try
                {
                    Console.WriteLine("Enter the maximum allowed word length please: ");
                    simbolValue = Convert.ToInt32(Console.ReadLine());
                    Console.WriteLine("Should the program removes punctuation marks (1 - yes, 2 - no) ? ");
                    punctMark = Convert.ToInt32(Console.ReadLine());
                    flag = false;

                    if (punctMark == 0)
                    {
                        mark = false;
                    }

                    bool anotherFlag = true;

                    while (anotherFlag)
                    {
                        try
                        {
                            Console.WriteLine("Input full path to file please: ");
                            string file = Console.ReadLine();
                            anotherFlag = false;

                            string fileDir = Path.GetDirectoryName(file); // ?
                            string fileName = Path.GetFileName(file); // ?

                            string[] lines = File.ReadAllLines(file);
                            text = new string[lines.Length];

                            int i = 0;

                            foreach (string line in lines)
                            {
                                if (mark)
                                {
                                    var lineWithoutRegEx = Regex.Replace(line, "-.?!)(,:;\"", " ");

                                    // may be bug due to StringSplitOptions.RemoveEmptyEntries
                                    string[] words = lineWithoutRegEx.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
                                    foreach (string word in words)
                                    {
                                        if (word.Length < simbolValue)
                                        {
                                            word.Replace(word, "");
                                            text[i] = word;
                                            i++;
                                        }
                                    }
                                } else
                                {
                                    string[] words = line.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
                                    foreach (string word in words)
                                    {
                                        if (word.Length < simbolValue)
                                        {
                                            word.Replace(word, "");
                                            text[i] = word;
                                            i++;
                                        }
                                    }
                                }
                            }
                        } catch (Exception)
                        {
                            Console.WriteLine("The file doesn't exist. Enter the correct file name please.");
                        }

                        Console.WriteLine("Input full path to file where information will be written please: ");
                        String newPath = Console.ReadLine();
                        File.WriteAllLines(newPath, text);
                    }
                } catch (Exception)
                {
                    Console.WriteLine("Number allowed only. Try again please.");
                }
            }
        }
    }
}
