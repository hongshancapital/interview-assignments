<?php
namespace App\Controller\Frontend;

use App\Controller\Frontend\BaseController;
use Symfony\Component\Routing\Annotation\Route;

use App\Entity\User;


class IndexController extends BaseController 
{
    /**
     * @Route("/", name="admin")
     */
    public function index()
    {
        return $this->render('frontend/index/index.html.twig');
    }
}