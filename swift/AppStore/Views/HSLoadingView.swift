//
//  HSLoadingView.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import SwiftUI

struct HSLoadingView: View {
    var body: some View {
        VStack {
            Spacer()
            HStack {
                Spacer()
                ActivityView()
                Spacer()
            }
            Spacer()
        }
    }
}

struct HSLoadingView_Previews: PreviewProvider {
    static var previews: some View {
        HSLoadingView()
    }
}
