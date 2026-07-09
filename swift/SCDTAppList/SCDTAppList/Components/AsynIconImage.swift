//
//  AsynIconImage.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/15.
//

import SwiftUI

enum AsynImageLoadingStatus{
    case loading
    case success
    case failure
}

 struct AsynIconImage:View {
    let url: URL
    
    let imageLoadingeFinish: (_ status: AsynImageLoadingStatus) -> Void

    
    var body: some View {
        VStack{
            AsyncImage(url: url) { [self] phase in
                self.processSuccessImage(phase)
            }
        }
        
    }
    
     func processSuccessImage(_ phase: AsyncImagePhase) -> AnyView{
        if let image = phase.image {
            /// displays the loaded image.
            ///
            self.imageLoadingeFinish(.success)
            return AnyView (
                image
                    .resizable()
                    .scaledToFit()
            )
        }  else if phase.error != nil {
            self.imageLoadingeFinish(.failure)
            return AnyView(
                Image(systemName: "photo")
            )
        }else {
            self.imageLoadingeFinish(.loading)
            return AnyView(
                ProgressView().progressViewStyle(.circular)
            )
        }
    }
    
    
}

#if DEBUG
struct AsynIconImage_Previews: PreviewProvider {
    static var previews: some View {
        AsynIconImage(url: URL(string: "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/a5/6c/00/a56c00c1-aead-18fc-cca7-22c09f934a5f/AppIcon-0-0-1x_U007emarketing-0-7-0-0-85-220.png/60x60bb.jpg")!){ status in
            
        }
            .frame(width: 100,height: 100)
        
//        AsynIconImage(url: URL(string: "xx")!,loadStatus: .constant(.loadding))
//                    .frame(width: 100,height: 100)

    }
}
#endif
