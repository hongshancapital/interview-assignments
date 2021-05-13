<?php
namespace App\Controller\Api;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Validator\Validator\ValidatorInterface;

use App\Entity\User;


class ApiController extends AbstractController
{
    protected $entityManager = null;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;       
    }    

    /**
     * @Route("/api/register", methods={"post"}, name="api.register")
     */
    public function register(Request $request, ValidatorInterface $validator)
    {
        try {
            $postData = json_decode( $request->getContent(), true);
            $username = $postData['username'] ?? '';
            $password = $postData['password'] ?? '';
            $passwordRepeat = $postData['passwordRepeat'] ?? '';

            $user = new User();
            $user->setUsername($username);
            $user->setPassword($password);
            $user->setPasswordRepeat($passwordRepeat);
            

            $errors = $validator->validate($user);

            if (count($errors) > 0) {     
                return $this->json(['code'=>1, 'msg'=> $errors[0]->getMessage()]);
            }
            
            $this->entityManager->persist($user);
            $this->entityManager->flush();

            return $this->json(['code'=>0, 'msg'=>'注册成功']);
        } catch (\Throwable $e) {
            return $this->json(['code'=>1, 'msg'=> $e->getMessage()]);
        }
    }

    /**
     * @Route("/api/users", name="api.users")
     */
    public function users()
    {
        $users = $this->entityManager->getRepository(User::class)->createQueryBuilder('u')
            ->select('u.username')->orderBy('u.id', 'asc')->getQuery()->getArrayResult();
        return $this->json(['code'=>0, 'msg'=>'', 'data'=>$users]);
    }
}