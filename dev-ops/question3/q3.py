data_dict = dict()
with open('q3.data') as data:
    lines = data.readlines()

for line in lines:
    key = line.strip().split(' ')[0]
    value = line.strip().split(' ')[1]
    data_dict[key] = value

sorted_dict = sorted(data_dict.items(),
                     key=lambda kv: (kv[1], kv[0]),
                     reverse=True)
sorted_list = [(k+" "+v) for k, v in sorted_dict]
print('\n'.join(sorted_list))
