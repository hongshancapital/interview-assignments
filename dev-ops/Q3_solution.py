#!/usr/bin/python3

import click


@click.command()
@click.option('--filename', default='', help='The file with the numbers')
def sort_num(filename):
    if not filename:
        click.echo('Error: the file name is empty.')
        return False
    
    nums = []
    try:
        with open(filename, mode='r') as f:
            for line in f:
                linelist = line.split()
                if len(linelist) > 1:
                    nums.append(linelist[1])
    except FileNotFoundError:
        click.echo('Error: can not find the file.')
        return False
    except FileExistsError:
        click.echo('Error: can not open the file.')
        return False

    nums.sort()
    print(nums)
    return True

if __name__ == '__main__':
    sort_num()
