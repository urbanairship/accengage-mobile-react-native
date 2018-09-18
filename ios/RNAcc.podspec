
Pod::Spec.new do |s|

  s.name         = 'RNAcc'
  s.version      = "1.0.1"
  s.summary      = 'Connect with your mobile users via push notifications and in-app messaging thanks to Accengage.'
  s.description  = <<-DESC
                   Connect with your mobile users via push notifications and in-app messaging thanks to Accengage, the most advanced Mobile CRM Solution available on the market to date.

                   With more formats than ever, dynamic segmentation and targeting, real-time geofencing, notifications personalization, automation, interconnection with your CRM and Analytics Tools for next generation mobile CRM scenarios, A/B Testing, multi-app messages, badge management, Facebook notifications…

                   Accengage has it all, and more!
                   Much more.
                   DESC
  s.homepage     = 'https://www.accengage.com/'
  s.license      = { :type => 'proprietary', :text => <<-LICENSE
                            Copyright 2010 - present Accengage. All rights reserved.
                        LICENSE
                   }
  s.author       = { 'Accengage' => 'help@accengage.com' }
  s.platform = :ios
  s.ios.deployment_target = '8.0'
  s.source       = { :git => "https://github.com/Accengage/accengage-mobile-react-native.git", :tag => "master" }
  s.source_files  = "RNAcc/**/*.{h,m}"
  s.requires_arc = true

end

  
