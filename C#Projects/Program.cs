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
            Console.WriteLine("So let's start!\n");

            bool flag = true;
            int simbolValue;
            int punctMark;
            bool mark = true;
            string file;

            while (true)
            {
                try
                {
                    Console.WriteLine("Enter the maximum allowed word length please: ");
                    simbolValue = Convert.ToInt32(Console.ReadLine());
                    break;
                }
                catch (Exception)
                {
                    Console.WriteLine("Numbers allowed only.");
                }
            }

            while (flag)
            {
                try
                {
                    Console.WriteLine("Should the program removes punctuation marks (1 - yes, 0 - no) ? ");
                    punctMark = Convert.ToInt32(Console.ReadLine());
                    switch (punctMark)
                    {
                        case 1:
                            flag = false;
                            break;
                        case 0:
                            flag = false;
                            mark = false;
                            break;
                        default:
                            Console.WriteLine("Enter 1 of 0 please.");
                            break;
                    }
                }
                catch (Exception)
                {
                    Console.WriteLine("Numbers allowed only.");
                }
            }

            while (true)
            {
                try
                {
                    Console.WriteLine("Input full path to file please: ");
                    file = Console.ReadLine();
                    break;
                }
                catch (Exception)
                {
                    Console.WriteLine("The file doesn't exist. Enter the correct file name please.");
                }
            }

            string[] lines = File.ReadAllLines(file);
            string[] text = new string[lines.Length];
            string[] newLines = new string[lines.Length];

            int i = 0;
            Regex rgx = new Regex("[^a-zA-Z0-9 -]");

            if (mark)
            {
                foreach (string line in lines)
                {
                    newLines[i] = rgx.Replace(line, "");
                    i++;
                }
            }

            i = 0;
            int j = 0;

            foreach (string line in lines)
            {
                string[] words = line.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
                string[] newWords = new string[words.Length];
                foreach (string word in words)
                {
                    if (word.Length < simbolValue)
                    {
                        newWords[i] = word.Replace(word, " ");
                        i++;
                    }
                    else
                    {
                        newWords[i] = word;
                        i++;
                    }
                }
                text[j] = string.Join("", newWords);
                j++;
            }

            Console.WriteLine("Input full path to file where information will be written please: ");
            while (true)
            {
                try
                {
                    String newPath = Console.ReadLine();
                    File.WriteAllLines(newPath, text);
                    Console.WriteLine("The information was written to " + newPath + " path.");
                    break;
                }
                catch (Exception)
                {
                }
            }
        }
    }
}
