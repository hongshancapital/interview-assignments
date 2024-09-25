import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

const domainData = [
  {
    name: 'ppya',
    email: 'alice@prisma.io',
    links: {
      create: [
        {
          shortLink: 'https://ppay/cn/1234', 
          longLink: 'https://baidu.com/',
        },
      ],
    },
  },
];

async function main() {
  for (const u of domainData) {
    const user = await prisma.user.create({
      data: u,
    });
    console.log(`Created user with id: ${user.id}`);
  }
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });