#
# Be sure to run `pod lib lint GCore.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see https://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
    s.name             = 'GCore'
    s.version          = '0.1.0'
    s.summary          = 'A short description of GCore.'
    s.description      = 'A short description of GCore.'
    
    s.homepage         = 'www.abc.com'
    s.license          = { :type => 'MIT'}
    s.author           = { 'lizhao' => 'mail@.com' }
    s.source           = { :path => '.' }
    
    s.ios.deployment_target = '15.0'
    
    s.source_files = 'GCore/Classes/**/*'
     
    s.dependency 'Moya/Combine'
    s.dependency 'Result'
end
