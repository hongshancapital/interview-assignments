//
//  FooterLoadView.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/24.
//

import SwiftUI

struct FooterLoadView: View {
    
    public var loadMoreBlock: (() -> Void)? = nil;
    
    var body: some View {
        HStack(alignment: .center) {
            Spacer()
            ProgressView()
                .frame(width: 60, height: 60);
            Text("Loading...")
                .font(.system(size: 18))
                .foregroundColor(.gray)
            Spacer()
        }
        .frame(height: 60)
        .onAppear(perform: self.loadMoreBlock)
    }
}

struct FooterLoadView_Previews: PreviewProvider {
    static var previews: some View {
        FooterLoadView()
    }
}
