# Parallel-Computing-U.S.-Census-Diversity-Index

 The United States Census Bureau (USCB) estimates the number of persons in each county in each state, categorized by age, gender, race, and other factors. USCB uses six racial categories: White; Black or African American; American Indian or Alaska Native; Asian; Native Hawaiian or Other Pacific Islander; Two or more races.

For example, here are the USCB's population estimates for Monroe County, New York, as of July 1, 2017:

White 		573928
Black or African American 		121423
American Indian or Alaska Native 		3074
Asian 		29053
Native Hawaiian or Other Pacific Islander 		517
Two or more races 		19667
Total 		747662

The diversity index D for a population is the probability that two randomly chosen individuals in that population will be of different races. The diversity index is calculated with this formula, where Ni is the number of individuals in racial category i and T is the total number of individuals:

	
                                                    D  =  (1/T)^2 Σ Ni (T − Ni)


For example, the diversity index of Monroe County, New York as of July 1, 2017 is 0.38216. The diversity index should be calculated using double precision floating point (type double).

I have downloaded a census dataset from the USCB web site. The dataset contains information for every county in every state from 2010 to 2017. I installed the full dataset on the tardis machine in a file named /var/tmp/ark/USCensus/cc-est2017-alldata.csv. You can log into tardis and look at the dataset. I also partitioned the dataset and installed the partitions on the local hard disks of the dr00 through dr09 nodes of the tardis cluster. Each node has one file named /var/tmp/ark/USCensus/cc-est2017-alldata.csv. Each file totals about 14 megabytes, and the whole dataset totals about 140 megabytes.

Each record in the dataset (each line in the file) consists of several fields. Each field is separated from the next field by a comma. The fields contain the following information. Fields are indexed from left to right starting at 0. Only the fields needed for this project are listed.

Index 		Name 		Contents 		Type
3 		STNAME 		State name 		String
4 		CTYNAME 		County name 		String
5 		YEAR 		Year 		Integer
6 		AGEGRP 		Age group 		Integer
10 		WA_MALE 		White male population 		Integer
11 		WA_FEMALE 		White female population 		Integer
12 		BA_MALE 		Black or African American male population 		Integer
13 		BA_FEMALE 		Black or African American female population 		Integer
14 		IA_MALE 		American Indian or Alaska Native male population 		Integer
15 		IA_FEMALE 		American Indian or Alaska Native female population 		Integer
16 		AA_MALE 		Asian male population 		Integer
17 		AA_FEMALE 		Asian female population 		Integer
18 		NA_MALE 		Native Hawaiian or Other Pacific Islander male population 		Integer
19 		NA_FEMALE 		Native Hawaiian or Other Pacific Islander female population 		Integer
20 		TOM_MALE 		Two or more races male population 		Integer
21 		TOM_FEMALE 		Two or more races female population 		Integer

The YEAR field is an integer giving the record's year:
1 = April 1, 2010 census population
2 = April 1, 2010 population estimates base
3 = July 1, 2010 population estimate
4 = July 1, 2011 population estimate
5 = July 1, 2012 population estimate
6 = July 1, 2013 population estimate
7 = July 1, 2014 population estimate
8 = July 1, 2015 population estimate
9 = July 1, 2016 population estimate
10 = July 1, 2017 population estimate

The AGEGRP field is an integer giving the record's age group. We are only interested in records with AGEGRP = 0; such records contain the total population for all age groups.

The number of individuals in a racial category is the sum of the number of males and the number of females. For example, the number of white individuals is WA_MALE + WA_FEMALE.

The USCB web site has a document that describes the census dataset in full detail. 

### Program Input and Output

The census data analysis program's command line arguments are a list of comma-separated node names, the name of the file containing the census data, the year (an integer from 1 to 10), and zero or more state names. (The state names might include spaces and so must be enclosed in quotation marks, such as "New York".) If there are no state names, the program must analyze all counties in all states for the given year. If there are one or more state names, the program must analyze all counties in the given states for the given year. The number of mapper threads per mapper task is also specified using the pj2 program's "threads=" option.

The census data analysis program's output consists of a series of sections. Each section reports results for one state. The sections appear in ascending order of the state name.

Each section consists of a series of lines. The first line consists of the state name, two tab characters, the state's overall diversity index calculated from the total population of that state, and a newline. The second and subsequent lines each consist of a tab character, the county name, a tab character, the county's diversity index calculated from the population of just that county, and a newline. The county lines are printed in descending order of the diversity index. If multiple county lines have the same diversity index, those county lines are printed in ascending order of the county name.

You must use the following code to print the first line in each section:

    System.out.printf ("%s\t\t%.5g%n", stateName, diversityIndex);

You must use the following code to print the second and subsequent lines in each section:

    System.out.printf ("\t%s\t%.5g%n", countyName, diversityIndex);

A PJMR job is a cluster parallel program. This means that, when running on the tardis cluster, you must include the "jar=" option on the pj2 command line. The JAR file must contain all the Java class files (.class files) in your project.

Here is an example of the analysis program running on the tardis cluster. (Note: The lines reporting job statistics were printed by pj2; they are not part of the analysis program's output.)

```
$ java pj2 jar=p4.jar DivIndex dr00,dr01,dr02,dr03,dr04,dr05,dr06,dr07,dr08,dr09 /var/tmp/ark/USCensus/cc-est2017-alldata.csv 10 "New York" "Delaware"
Job 159 launched Mon Nov 19 09:54:44 EST 2018
Job 159 started Mon Nov 19 09:54:44 EST 2018
Delaware		0.45971
	New Castle County	0.50210
	Kent County	0.48199
	Sussex County	0.29902
New York		0.47584
	Queens County	0.65390
	Kings County	0.62253
	Bronx County	0.60372
	New York County	0.53530
	Westchester County	0.42755
	Nassau County	0.41871
	Richmond County	0.40180
	Albany County	0.39836
	Monroe County	0.38216
	Rockland County	0.37691
	Schenectady County	0.36599
	Erie County	0.34747
	Onondaga County	0.34385
	Tompkins County	0.33426
	Orange County	0.33248
	Dutchess County	0.32254
	Franklin County	0.28961
	Suffolk County	0.27567
	Sullivan County	0.26730
	Oneida County	0.25274
	Broome County	0.25194
	Rensselaer County	0.24370
	Jefferson County	0.23035
	Ulster County	0.22939
	Niagara County	0.22235
	Chemung County	0.21365
	Greene County	0.18810
	Orleans County	0.18458
	Columbia County	0.18228
	Seneca County	0.15810
	Putnam County	0.15491
	Clinton County	0.15324
	Cattaraugus County	0.15288
	Wyoming County	0.14762
	Cayuga County	0.14600
	Genesee County	0.13357
	Saratoga County	0.13325
	Montgomery County	0.12806
	Livingston County	0.12422
	Wayne County	0.12255
	Chautauqua County	0.12089
	St. Lawrence County	0.12029
	Ontario County	0.12029
	Essex County	0.11883
	Otsego County	0.11324
	Washington County	0.10925
	Cortland County	0.10132
	Madison County	0.10068
	Steuben County	0.096877
	Delaware County	0.091645
	Fulton County	0.091441
	Allegany County	0.085892
	Warren County	0.082873
	Schoharie County	0.081710
	Oswego County	0.075486
	Herkimer County	0.073554
	Hamilton County	0.069286
	Chenango County	0.067883
	Schuyler County	0.067763
	Tioga County	0.067176
	Yates County	0.060257
	Lewis County	0.057358
Job 159 finished Mon Nov 19 09:54:46 EST 2018 time 1689 msec
```

Software Requirements

   The program must be run by typing this command line on the tardis cluster:

        java pj2 jar=<jar> threads=<NT> DivIndex <nodes> <file> <year> [ "<state>" ... ]

        <jar> is the name of the JAR file containing all of the program's Java class files.
        <NT> is the number of mapper threads per mapper task; if omitted, the default is 1.
        <nodes> is a comma-separated list of cluster node names on which to run the analysis. One or more node names must be specified.
        <file> is the name of the file on each node's local hard disk containing the census data to be analyzed.
        <year> is the year to be analyzed. It must be an integer from 1 to 10.
        <state> is a state name to be analyzed. There can be zero or more state names. A particular state name must not appear more than once. 

   Note: This means that the program's class must be named DivIndex, this class must not be in a package, and this class must extend class edu.rit.pjmr.PjmrJob. 
