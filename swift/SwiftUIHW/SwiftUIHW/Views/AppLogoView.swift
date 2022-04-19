//
//  AppLogoView.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/16/22.
//

import SwiftUI

struct AppLogoView: View {
    var image: Image?
    var loadingView: ProgressIndicatorView?
    
    init() {
        self.loadingView = ProgressIndicatorView(showText: false, state: .default)
    }
    
    var body: some View {
        if image != nil {
            image!
                .cornerRadius(6)
        }
        else {
            loadingView!
        }
    }
}

struct AppLogoView_Previews: PreviewProvider {
    static var previews: some View {
        AppLogoView()
    }
}
