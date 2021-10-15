<?php


namespace Tests\Http\Controllers;



use Illuminate\Support\Facades\Session;
use Tests\TestCase;

class UserControllerTest extends TestCase
{
    public function testRegister()
    {
        $response = $this->get('/');
        $response->assertStatus(200);
        $response = $this->post('/api/register');
        $response->assertStatus(302);

        $response = $this->post('/api/register', [
            'username' => 'name',
            'password' => 'aaa1234',
            'repeat_password' => 'aaa1234',
        ]);
        $response->assertStatus(302);

        $response = $this->post('/api/register', [
            'username' => 'name',
            'password' => 'aaaaaaa',
            'repeat_password' => 'aaaaaaa',
        ]);
        $response->assertStatus(302);

        $response = $this->post('/api/register', [
            'username' => 'namenamenamenamenamenamename',
            'password' => 'aaaaaaa',
            'repeat_password' => 'aaaaaaa',
        ]);
        $response->assertStatus(302);

        $response = $this->post('/api/register', [
            'username' => 'name',
            'password' => 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
            'repeat_password' => 'aaaaaaa',
        ]);
        $response->assertStatus(302);

        $response = $this->post('/api/register', [
            'username' => 'name',
            'password' => 'aaa132',
            'repeat_password' => 'aaa134',
        ]);

        $response->assertStatus(302);

        $response = $this->post('/api/register', [
            'username' => '1name',
            'password' => 'aaa132',
            'repeat_password' => 'aaa134',
        ]);

        $response->assertStatus(302);


        $response = $this->post('/api/register', [
            'username' => '1name',
            'password' => 'aaa13',
            'repeat_password' => 'aaa13',
        ]);

        $response->assertStatus(302);


        $response = $this->post('/api/register', [
            'username' => 'nam*e',
            'password' => 'aaa132',
            'repeat_password' => 'aaa132',
        ]);

        $response->assertStatus(302);

        $response = $this->post('/api/register', [
            'username' => 'name',
            'password' => 'aaa132',
            'repeat_password' => 'aaa132',
        ]);
        $response->assertStatus(200);

        $response = $this->post('/api/register', [
            'username' => 'name',
            'password' => 'aaa132',
            'repeat_password' => 'aaa132',
        ]);
        $response->assertStatus(302);
    }
}
