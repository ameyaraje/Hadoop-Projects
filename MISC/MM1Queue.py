'''
Author: Ameya Raje
Student ID: 54729960    
'''


import numpy as np


def MM1Q():

    time = 200
    rateOfArrival = 1
    rateOfService = [1.1, 1.5, 2, 3, 9]
    arrivalTimes = []
    arrivalTime = 0
    arrIntervals = []

    while arrivalTime < time:
        arrInt = np.random.exponential(1.0 / rateOfArrival)
        arrIntervals.append(arrInt)
        arrivalTime += arrInt
        arrivalTimes.append(arrivalTime)

    print "Jobs- ", len(arrivalTimes)
    print "Avg arrival time- ", np.mean(arrivalTimes)
    print "Avg difference in arrival times- ", np.mean(arrIntervals)

    for mu in rateOfService:
        times = len(arrivalTimes)
        serviceTimes = np.random.exponential(1.0 / mu, times)
        respTimes = []
        compTimes = []

        for job in xrange(times):
            arrivalTime = arrivalTimes[job]
            serviceTime = serviceTimes[job]
            if job == 0:
                compTime = arrivalTime + serviceTime
            elif compTimes[job-1] < arrivalTime:
                compTime = arrivalTime + serviceTime
            else:
                compTime = compTimes[job-1] + serviceTime

            compTimes.append(compTime)
            respTimes.append(compTime-arrivalTime)
            lastTask = compTime

        print "\n\n"
        print "Avg service time for ", mu, " - ", np.mean(serviceTimes)    
        print "Average response time for ", mu, " - ", np.mean(respTimes)
        print "Server utilization for ", mu, " - ", sum(serviceTimes) / lastTask



MM1Q()
