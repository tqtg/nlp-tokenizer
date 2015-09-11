from random import randint

inFile = open("tonghop_sentences.txt", "rb")

train = []
for line in inFile:
	train.append(line)

print len(train)

test = []
for i in range(0, 2000):
	index = randint(0, len(train) - 1)
	test.append(train.pop(index))

print "Training set: " + str(len(train))
print "Test set: " + str(len(test))

trainFile = open("train.txt", "wb")
testFile = open("test.txt", "wb")

for sen in train:
	trainFile.write(sen)

for sen in test:
	testFile.write(sen)