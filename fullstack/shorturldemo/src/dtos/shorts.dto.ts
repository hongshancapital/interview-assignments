
import { ValidatorConstraint, ValidatorConstraintInterface, ValidationArguments, Validate } from 'class-validator';



const prevArr = ['http', 'https'];

@ValidatorConstraint({ name: 'customURI', async: false })
export class CustomURI implements ValidatorConstraintInterface {
  validate(text: string, args: ValidationArguments) {
    // console.log('text', text)
    return !!prevArr.find(x=> text.startsWith(x));
  }

  defaultMessage(args: ValidationArguments) {
    // here you can provide default error message if validation failed
    return 'Text ($value) is not real url!';
  }
}

export class CreateShortDto {
  @Validate(CustomURI)
  public origin: string;

}



