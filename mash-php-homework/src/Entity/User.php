<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * @ORM\Entity(repositoryClass=UserRepository::class)
 * @UniqueEntity("username", message="用户名已存在")
 */
class User
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=100, unique=true)
     * @Assert\NotBlank(message="用户名不能为空")    
     * @Assert\Regex(
     *     pattern="/^[a-zA-Z]|_]/",       
     *     message="用户名只能以英文字母或下划线开头"
     * )   
     * @Assert\Regex(
     *     pattern="/[\W]+/", 
     *     match=false,      
     *     message="用户名只能包含英文字母 下划线或数字"
     * )
     *   
     */
    private $username;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="密码不能为空")
     * @Assert\Length(min=6, minMessage="密码长度至少6位")
     * @Assert\Regex(
     *     pattern="/^(?![0-9]+$)(?![a-zA-Z]+$)[A-Za-z_0-9]{1,}$/",       
     *     message="密码必须有大写字母，小写字母或数字中的两项"
     * ) 
     * @Assert\Regex(
     *     pattern="/[0-9]{3,}+/", 
     *     match=false,      
     *     message="密码不能含有 3 位以上的连续数字"
     * )
     * @Assert\EqualTo(
     *     message = "初始密码和重复密码不一致",
     *     propertyPath="getPasswordRepeat"
     * )
     */
    private $password;

    private $passwordRepeat;

    /**
     * @ORM\Column(type="datetime", nullable=true)
     */
    private $createdAt;

    public function __construct()
    {
        $this->createdAt = new \Datetime();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUsername(): ?string
    {
        return $this->username;
    }

    public function setUsername(string $username): self
    {
        $this->username = $username;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getPasswordRepeat(): ?string
    {
        return $this->passwordRepeat;
    }

    public function setPasswordRepeat(string $passwordRepeat): self
    {
        $this->passwordRepeat = $passwordRepeat;

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->createdAt;
    }

    public function setCreatedAt(?\DateTimeInterface $createdAt): self
    {
        $this->createdAt = $createdAt;

        return $this;
    }
}
