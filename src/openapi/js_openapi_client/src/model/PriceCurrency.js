/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 *
 */

import ApiClient from '../ApiClient';

/**
 * The PriceCurrency model module.
 * @module model/PriceCurrency
 * @version v0
 */
class PriceCurrency {
    /**
     * Constructs a new <code>PriceCurrency</code>.
     * @alias module:model/PriceCurrency
     */
    constructor() { 
        
        PriceCurrency.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>PriceCurrency</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/PriceCurrency} obj Optional instance to populate.
     * @return {module:model/PriceCurrency} The populated <code>PriceCurrency</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new PriceCurrency();

            if (data.hasOwnProperty('currencyCode')) {
                obj['currencyCode'] = ApiClient.convertToType(data['currencyCode'], 'String');
            }
            if (data.hasOwnProperty('defaultFractionDigits')) {
                obj['defaultFractionDigits'] = ApiClient.convertToType(data['defaultFractionDigits'], 'Number');
            }
            if (data.hasOwnProperty('numericCode')) {
                obj['numericCode'] = ApiClient.convertToType(data['numericCode'], 'Number');
            }
            if (data.hasOwnProperty('symbol')) {
                obj['symbol'] = ApiClient.convertToType(data['symbol'], 'String');
            }
            if (data.hasOwnProperty('numericCodeAsString')) {
                obj['numericCodeAsString'] = ApiClient.convertToType(data['numericCodeAsString'], 'String');
            }
            if (data.hasOwnProperty('displayName')) {
                obj['displayName'] = ApiClient.convertToType(data['displayName'], 'String');
            }
        }
        return obj;
    }


}

/**
 * @member {String} currencyCode
 */
PriceCurrency.prototype['currencyCode'] = undefined;

/**
 * @member {Number} defaultFractionDigits
 */
PriceCurrency.prototype['defaultFractionDigits'] = undefined;

/**
 * @member {Number} numericCode
 */
PriceCurrency.prototype['numericCode'] = undefined;

/**
 * @member {String} symbol
 */
PriceCurrency.prototype['symbol'] = undefined;

/**
 * @member {String} numericCodeAsString
 */
PriceCurrency.prototype['numericCodeAsString'] = undefined;

/**
 * @member {String} displayName
 */
PriceCurrency.prototype['displayName'] = undefined;






export default PriceCurrency;
