require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|

  s.name         = 'RNAcc'
  s.version      = package['version']
  s.summary      = package['description']
  s.description  = <<-DESC
                   Connect with your mobile users via push notifications and in-app messaging thanks to Accengage, the most advanced Mobile CRM Solution available on the market to date.

                   With more formats than ever, dynamic segmentation and targeting, real-time geofencing, notifications personalization, automation, interconnection with your CRM and Analytics Tools for next generation mobile CRM scenarios, A/B Testing, multi-app messages, badge management, Facebook notifications…

                   Accengage has it all, and more!
                   Much more.
                   DESC
  s.homepage     = package['homepage']
  s.license      = { :type => 'proprietary', :text => <<-LICENSE
                            Copyright 2010 - present Accengage. All rights reserved.
                        LICENSE
                   }
  s.author       = package['author']
  s.platform     = :ios, "9.0"
  s.ios.deployment_target = '9.0'
  s.source       = { :git => "https://github.com/Accengage/accengage-mobile-react-native.git", :tag => s.version }
  s.source_files  = "ios/RNAcc/*.{h,m}"
  s.requires_arc = true
  s.ios.vendored_frameworks = "ios/Frameworks/Accengage.framework"
  s.dependency "React"

end
