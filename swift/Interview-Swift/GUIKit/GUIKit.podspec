#
# Be sure to run `pod lib lint GUIKit.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see https://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'GUIKit'
  s.version          = '0.1.0'
  s.summary          = 'A short description of GUIKit.'

  s.homepage         = 'http://'
  s.description      =  'A short description of GUIKit.'
  s.license          = { :type => 'MIT' }
  s.author           = { 'lizhao' => 'lizhao15431230@icloud.com' }
  s.source           = { :git => '.' }
  s.ios.deployment_target = '15.0'

  s.source_files = 'GUIKit/Classes/**/*' 
end
